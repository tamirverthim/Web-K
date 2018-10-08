/*
 * {{{ header & license
 * Copyright (c) 2004, 2005 Joshua Marinacci
 * Copyright (c) 2006, 2007 Wisconsin Courts System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package com.earnix.kbrowser.render;

import com.earnix.kbrowser.css.constants.CSSName;
import com.earnix.kbrowser.css.constants.IdentValue;
import com.earnix.kbrowser.css.newmatch.CascadedStyle;
import com.earnix.kbrowser.css.parser.FSRGBColor;
import com.earnix.kbrowser.css.style.CalculatedStyle;
import com.earnix.kbrowser.css.style.CssContext;
import com.earnix.kbrowser.css.style.FSDerivedValue;
import com.earnix.kbrowser.css.style.derived.BorderPropertySet;
import com.earnix.kbrowser.css.style.derived.LengthValue;
import com.earnix.kbrowser.css.style.derived.RectPropertySet;
import com.earnix.kbrowser.extend.FSImage;
import com.earnix.kbrowser.extend.ReplacedElement;
import com.earnix.kbrowser.layout.BlockBoxing;
import com.earnix.kbrowser.layout.BlockFormattingContext;
import com.earnix.kbrowser.layout.BoxBuilder;
import com.earnix.kbrowser.layout.BreakAtLineContext;
import com.earnix.kbrowser.layout.CounterFunction;
import com.earnix.kbrowser.layout.FloatManager;
import com.earnix.kbrowser.layout.InlineBoxing;
import com.earnix.kbrowser.layout.InlinePaintable;
import com.earnix.kbrowser.layout.LayoutContext;
import com.earnix.kbrowser.layout.PaintingInfo;
import com.earnix.kbrowser.layout.PersistentBFC;
import com.earnix.kbrowser.layout.Styleable;
import com.earnix.kbrowser.newtable.TableRowBox;
import lombok.extern.slf4j.Slf4j;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A block box as defined in the CSS spec.  It also provides a base class for
 * other kinds of block content (for example table rows or cells).
 */
@Slf4j
public class BlockBox extends Box implements InlinePaintable {

    public static final int POSITION_VERTICALLY = 1;
    public static final int POSITION_HORIZONTALLY = 2;
    public static final int POSITION_BOTH = POSITION_VERTICALLY | POSITION_HORIZONTALLY;

    public static final int CONTENT_UNKNOWN = 0;
    public static final int CONTENT_INLINE = 1;
    public static final int CONTENT_BLOCK = 2;
    public static final int CONTENT_EMPTY = 4;

    protected static final int NO_BASELINE = Integer.MIN_VALUE;

    private MarkerData _markerData;

    private int _listCounter;

    private PersistentBFC _persistentBFC;

    private Box _staticEquivalent;

    private boolean _needPageClear;

    private ReplacedElement _replacedElement;

    private int _childrenContentType;

    private List _inlineContent;

    private boolean _topMarginCalculated;
    private boolean _bottomMarginCalculated;
    private MarginCollapseResult _pendingCollapseCalculation;

    private int _minWidth;
    private int _maxWidth;
    private boolean _minMaxCalculated;

    private boolean _dimensionsCalculated;
    private boolean _needShrinkToFitCalculatation;

    private CascadedStyle _firstLineStyle;
    private CascadedStyle _firstLetterStyle;

    private FloatedBoxData _floatedBoxData;

    private int _childrenHeight;

    private boolean _fromCaptionedTable;

    public BlockBox() {
        super();
    }

    public BlockBox copyOf() {
        BlockBox result = new BlockBox();
        result.setStyle(getStyle());
        result.setElement(getElement());

        return result;
    }

    protected String getExtraBoxDescription() {
        return "";
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        String className = getClass().getName();
        result.append(className.substring(className.lastIndexOf('.') + 1));
        result.append(": ");
        if (getElement() != null && !isAnonymous()) {
            result.append("<");
            result.append(getElement().nodeName());
            result.append("> ");
        }
        if (isAnonymous()) {
            result.append("(anonymous) ");
        }
        if (getPseudoElementOrClass() != null) {
            result.append(':');
            result.append(getPseudoElementOrClass());
            result.append(' ');
        }
        result.append('(');
        result.append(getStyle().getIdent(CSSName.DISPLAY).toString());
        result.append(") ");

        if (getStyle().isRunning()) {
            result.append("(running) ");
        }

        result.append('(');
        switch (getChildrenContentType()) {
            case CONTENT_BLOCK:
                result.append('B');
                break;
            case CONTENT_INLINE:
                result.append('I');
                break;
            case CONTENT_EMPTY:
                result.append('E');
                break;
        }
        result.append(") ");

        result.append(getExtraBoxDescription());

        appendPositioningInfo(result);
        result.append("(" + getAbsX() + "," + getAbsY() + ")->(" + getWidth() + " x " + getHeight() + ")");
        return result.toString();
    }

    protected void appendPositioningInfo(StringBuffer result) {
        if (getStyle().isRelative()) {
            result.append("(relative) ");
        }
        if (getStyle().isFixed()) {
            result.append("(fixed) ");
        }
        if (getStyle().isAbsolute()) {
            result.append("(absolute) ");
        }
        if (getStyle().isFloated()) {
            result.append("(floated) ");
        }
    }

    public String dump(LayoutContext c, String indent, int which) {
        StringBuffer result = new StringBuffer(indent);

        ensureChildren(c);

        result.append(this);

        RectPropertySet margin = getMargin(c);
        result.append(" effMargin=[" + margin.top() + ", " + margin.right() + ", " +
                margin.bottom() + ", " + margin.right() + "] ");
        RectPropertySet styleMargin = getStyleMargin(c);
        result.append(" styleMargin=[" + styleMargin.top() + ", " + styleMargin.right() + ", " +
                styleMargin.bottom() + ", " + styleMargin.right() + "] ");

        if (getChildrenContentType() != CONTENT_EMPTY) {
            result.append('\n');
        }

        switch (getChildrenContentType()) {
            case CONTENT_BLOCK:
                dumpBoxes(c, indent, getChildren(), which, result);
                break;
            case CONTENT_INLINE:
                if (which == DUMP_RENDER) {
                    dumpBoxes(c, indent, getChildren(), which, result);
                } else {
                    for (Iterator i = getInlineContent().iterator(); i.hasNext(); ) {
                        Styleable styleable = (Styleable) i.next();
                        if (styleable instanceof BlockBox) {
                            BlockBox b = (BlockBox) styleable;
                            result.append(b.dump(c, indent + "  ", which));
                            if (result.charAt(result.length() - 1) == '\n') {
                                result.deleteCharAt(result.length() - 1);
                            }
                        } else {
                            result.append(indent + "  ");
                            result.append(styleable.toString());
                        }
                        if (i.hasNext()) {
                            result.append('\n');
                        }
                    }
                }
                break;
        }

        return result.toString();
    }

    public void paintListMarker(RenderingContext c) {
        if (!getStyle().isVisible()) {
            return;
        }

        if (getStyle().isListItem()) {
            ListItemPainter.paint(c, this);
        }
    }

    public Rectangle getPaintingClipEdge(CssContext cssCtx) {
        Rectangle result = super.getPaintingClipEdge(cssCtx);

        // HACK Don't know how wide the list marker is (or even where it is)
        // so extend the bounding box all the way over to the left edge of
        // the canvas
        if (getStyle().isListItem()) {
            int delta = result.x;
            result.x = 0;
            result.width += delta;
        }

        return result;
    }

    public void paintInline(RenderingContext c) {
        if (!getStyle().isVisible()) {
            return;
        }

        getContainingLayer().paintAsLayer(c, this);
    }

    public boolean isInline() {
        Box parent = getParent();
        return parent instanceof LineBox || parent instanceof InlineLayoutBox;
    }

    public LineBox getLineBox() {
        if (!isInline()) {
            return null;
        } else {
            Box b = getParent();
            while (!(b instanceof LineBox)) {
                b = b.getParent();
            }
            return (LineBox) b;
        }
    }

    public void paintDebugOutline(RenderingContext c) {
        c.getOutputDevice().drawDebugOutline(c, this, FSRGBColor.RED);
    }

    public MarkerData getMarkerData() {
        return _markerData;
    }

    public void setMarkerData(MarkerData markerData) {
        _markerData = markerData;
    }

    public void createMarkerData(LayoutContext c) {
        if (getMarkerData() != null) {
            return;
        }

        StrutMetrics strutMetrics = InlineBoxing.createDefaultStrutMetrics(c, this);

        boolean imageMarker = false;

        MarkerData result = new MarkerData();
        result.setStructMetrics(strutMetrics);

        CalculatedStyle style = getStyle();
        IdentValue listStyle = style.getIdent(CSSName.LIST_STYLE_TYPE);

        String image = style.getStringProperty(CSSName.LIST_STYLE_IMAGE);
        if (!image.equals("none")) {
            result.setImageMarker(makeImageMarker(c, strutMetrics, image));
            imageMarker = result.getImageMarker() != null;
        }

        if (listStyle != IdentValue.NONE && !imageMarker) {
            if (listStyle == IdentValue.CIRCLE || listStyle == IdentValue.SQUARE ||
                    listStyle == IdentValue.DISC) {
                result.setGlyphMarker(makeGlyphMarker(strutMetrics));
            } else {
                result.setTextMarker(makeTextMarker(c, listStyle));
            }
        }

        setMarkerData(result);
    }

    private MarkerData.GlyphMarker makeGlyphMarker(StrutMetrics strutMetrics) {
        int diameter = (int) ((strutMetrics.getAscent() + strutMetrics.getDescent()) / 3);

        MarkerData.GlyphMarker result = new MarkerData.GlyphMarker();
        result.setDiameter(diameter);
        result.setLayoutWidth(diameter * 3);

        return result;
    }


    private MarkerData.ImageMarker makeImageMarker(
            LayoutContext c, StrutMetrics structMetrics, String image) {
        FSImage img = null;
        if (!image.equals("none")) {
            img = c.getUac().getImageResource(image).getImage();
            if (img != null) {
                StrutMetrics strutMetrics = structMetrics;
                if (img.getHeight() > strutMetrics.getAscent()) {
                    img.scale(-1, (int) strutMetrics.getAscent());
                }
                MarkerData.ImageMarker result = new MarkerData.ImageMarker();
                result.setImage(img);
                result.setLayoutWidth(img.getWidth() * 2);
                return result;
            }
        }
        return null;
    }

    private MarkerData.TextMarker makeTextMarker(LayoutContext c, IdentValue listStyle) {
        String text;

        int listCounter = getListCounter();
        text = CounterFunction.createCounterText(listStyle, listCounter);

        text += ".  ";

        int w = c.getTextRenderer().getWidth(
                c.getFontContext(),
                getStyle().getFSFont(c),
                text);

        MarkerData.TextMarker result = new MarkerData.TextMarker();
        result.setText(text);
        result.setLayoutWidth(w);

        return result;
    }

    public int getListCounter() {
        return _listCounter;
    }

    public void setListCounter(int listCounter) {
        _listCounter = listCounter;
    }

    public PersistentBFC getPersistentBFC() {
        return _persistentBFC;
    }

    public void setPersistentBFC(PersistentBFC persistentBFC) {
        _persistentBFC = persistentBFC;
    }

    public Box getStaticEquivalent() {
        return _staticEquivalent;
    }

    public void setStaticEquivalent(Box staticEquivalent) {
        _staticEquivalent = staticEquivalent;
    }

    public boolean isReplaced() {
        return _replacedElement != null;
    }

    public void calcCanvasLocation() {
        if (isFloated()) {
            FloatManager manager = _floatedBoxData.getManager();
            if (manager != null) {
                Point offset = manager.getOffset(this);
                setAbsX(manager.getMaster().getAbsX() + getX() - offset.x);
                setAbsY(manager.getMaster().getAbsY() + getY() - offset.y);
            }
        }

        LineBox lineBox = getLineBox();
        if (lineBox == null) {
            Box parent = getParent();
            if (parent != null) {
                setAbsX(parent.getAbsX() + parent.getTx() + getX());
                setAbsY(parent.getAbsY() + parent.getTy() + getY());
            } else if (isStyled() && getStyle().isAbsFixedOrInlineBlockEquiv()) {
                Box cb = getContainingBlock();
                if (cb != null) {
                    setAbsX(cb.getAbsX() + getX());
                    setAbsY(cb.getAbsY() + getY());
                }
            }
        } else {
            setAbsX(lineBox.getAbsX() + getX());
            setAbsY(lineBox.getAbsY() + getY());
        }

        if (isReplaced()) {
            Point location = getReplacedElement().getLocation();
            if (location.x != getAbsX() || location.y != getAbsY()) {
                getReplacedElement().setLocation(getAbsX(), getAbsY());
            }
        }
    }

    public void calcInitialFloatedCanvasLocation(LayoutContext c) {
        Point offset = c.getBlockFormattingContext().getOffset();
        FloatManager manager = c.getBlockFormattingContext().getFloatManager();
        setAbsX(manager.getMaster().getAbsX() + getX() - offset.x);
        setAbsY(manager.getMaster().getAbsY() + getY() - offset.y);
    }

    public void calcChildLocations() {
        super.calcChildLocations();

        if (_persistentBFC != null) {
            _persistentBFC.getFloatManager().calcFloatLocations();
        }
    }

    public boolean isNeedPageClear() {
        return _needPageClear;
    }

    public void setNeedPageClear(boolean needPageClear) {
        _needPageClear = needPageClear;
    }


    private void alignToStaticEquivalent() {
        if (_staticEquivalent.getAbsY() != getAbsY()) {
            setY(_staticEquivalent.getAbsY() - getAbsY());
            setAbsY(_staticEquivalent.getAbsY());
        }
    }

    public void positionAbsolute(CssContext cssCtx, int direction) {
        CalculatedStyle style = getStyle();

        Rectangle boundingBox = null;

        int cbContentHeight = getContainingBlock().getContentAreaEdge(0, 0, cssCtx).height;

        if (getContainingBlock() instanceof BlockBox) {
            boundingBox = getContainingBlock().getPaddingEdge(0, 0, cssCtx);
        } else {
            boundingBox = getContainingBlock().getContentAreaEdge(0, 0, cssCtx);
        }

        if ((direction & POSITION_HORIZONTALLY) != 0) {
            setX(0);
            if (!style.isIdent(CSSName.LEFT, IdentValue.AUTO)) {
                setX((int) style.getFloatPropertyProportionalWidth(CSSName.LEFT, getContainingBlock().getContentWidth(), cssCtx));
            } else if (!style.isIdent(CSSName.RIGHT, IdentValue.AUTO)) {
                setX(boundingBox.width -
                        (int) style.getFloatPropertyProportionalWidth(CSSName.RIGHT, getContainingBlock().getContentWidth(), cssCtx) - getWidth());
            }
            setX(getX() + boundingBox.x);
        }

        if ((direction & POSITION_VERTICALLY) != 0) {
            setY(0);
            if (!style.isIdent(CSSName.TOP, IdentValue.AUTO)) {
                setY((int) style.getFloatPropertyProportionalHeight(CSSName.TOP, cbContentHeight, cssCtx));
            } else if (!style.isIdent(CSSName.BOTTOM, IdentValue.AUTO)) {
                setY(boundingBox.height -
                        (int) style.getFloatPropertyProportionalWidth(CSSName.BOTTOM, cbContentHeight, cssCtx) - getHeight());
            }

            // Can't do this before now because our containing block
            // must be completed layed out
            int pinnedHeight = calcPinnedHeight(cssCtx);
            if (pinnedHeight != -1 && getCSSHeight(cssCtx) == -1) {
                setHeight(pinnedHeight);
                applyCSSMinMaxHeight(cssCtx);
            }

            setY(getY() + boundingBox.y);
        }

        calcCanvasLocation();

        if ((direction & POSITION_VERTICALLY) != 0 &&
                getStyle().isTopAuto() && getStyle().isBottomAuto()) {
            alignToStaticEquivalent();
        }

        calcChildLocations();
    }

    public void positionAbsoluteOnPage(LayoutContext c) {
        if (c.isPrint() &&
                (getStyle().isForcePageBreakBefore() || isNeedPageClear())) {
            forcePageBreakBefore(c, getStyle().getIdent(CSSName.PAGE_BREAK_BEFORE), false);
            calcCanvasLocation();
            calcChildLocations();

            setNeedPageClear(false);
        }
    }

    public ReplacedElement getReplacedElement() {
        return _replacedElement;
    }

    public void setReplacedElement(ReplacedElement replacedElement) {
        _replacedElement = replacedElement;
    }

    public void reset(LayoutContext c) {
        super.reset(c);
        setTopMarginCalculated(false);
        setBottomMarginCalculated(false);
        setDimensionsCalculated(false);
        setMinMaxCalculated(false);
        setChildrenHeight(0);
        if (isReplaced()) {
            getReplacedElement().detach(c);
            setReplacedElement(null);
        }
        if (getChildrenContentType() == BlockBox.CONTENT_INLINE) {
            removeAllChildren();
        }

        if (isFloated()) {
            _floatedBoxData.getManager().removeFloat(this);
            _floatedBoxData.getDrawingLayer().removeFloat(this);
        }

        if (getStyle().isRunning()) {
            c.getRootLayer().removeRunningBlock(this);
        }
    }

    private int calcPinnedContentWidth(CssContext c) {
        if (!getStyle().isIdent(CSSName.LEFT, IdentValue.AUTO) &&
                !getStyle().isIdent(CSSName.RIGHT, IdentValue.AUTO)) {
            Rectangle paddingEdge = getContainingBlock().getPaddingEdge(0, 0, c);

            int left = (int) getStyle().getFloatPropertyProportionalTo(
                    CSSName.LEFT, paddingEdge.width, c);
            int right = (int) getStyle().getFloatPropertyProportionalTo(
                    CSSName.RIGHT, paddingEdge.width, c);

            int result = paddingEdge.width - left - right - getLeftMBP() - getRightMBP();
            return result < 0 ? 0 : result;
        }

        return -1;
    }

    private int calcPinnedHeight(CssContext c) {
        if (!getStyle().isIdent(CSSName.TOP, IdentValue.AUTO) &&
                !getStyle().isIdent(CSSName.BOTTOM, IdentValue.AUTO)) {
            Rectangle paddingEdge = getContainingBlock().getPaddingEdge(0, 0, c);

            int top = (int) getStyle().getFloatPropertyProportionalTo(
                    CSSName.TOP, paddingEdge.height, c);
            int bottom = (int) getStyle().getFloatPropertyProportionalTo(
                    CSSName.BOTTOM, paddingEdge.height, c);


            int result = paddingEdge.height - top - bottom;
            return result < 0 ? 0 : result;
        }

        return -1;
    }

    protected void resolveAutoMargins(
            LayoutContext c, int cssWidth,
            RectPropertySet padding, BorderPropertySet border) {
        int withoutMargins =
                (int) border.left() + (int) padding.left() +
                        cssWidth +
                        (int) padding.right() + (int) border.right();
        if (withoutMargins < getContainingBlockWidth()) {
            int available = getContainingBlockWidth() - withoutMargins;

            boolean autoLeft = getStyle().isAutoLeftMargin();
            boolean autoRight = getStyle().isAutoRightMargin();

            if (autoLeft && autoRight) {
                setMarginLeft(c, available / 2);
                setMarginRight(c, available / 2);
            } else if (autoLeft) {
                setMarginLeft(c, available);
            } else if (autoRight) {
                setMarginRight(c, available);
            }
        }
    }

    private int calcEffPageRelativeWidth(LayoutContext c) {
        int totalLeftMBP = 0;
        int totalRightMBP = 0;

        boolean usePageRelativeWidth = true;

        Box current = this;
        while (true) {
            CalculatedStyle style = current.getStyle();
            if (style.isAutoWidth() && !style.isCanBeShrunkToFit()) {
                totalLeftMBP += current.getLeftMBP();
                totalRightMBP += current.getRightMBP();
            } else {
                usePageRelativeWidth = false;
                break;
            }

            if (current.getContainingBlock().isInitialContainingBlock()) {
                break;
            } else {
                current = current.getContainingBlock();
            }
        }

        if (usePageRelativeWidth) {
            PageBox currentPage = c.getRootLayer().getFirstPage(c, this);
            return currentPage.getContentWidth(c) - totalLeftMBP - totalRightMBP;
        } else {
            return getContainingBlockWidth() - getLeftMBP() - getRightMBP();
        }
    }

    public void calcDimensions(LayoutContext c) {
        calcDimensions(c, getCSSWidth(c));
    }

    protected void calcDimensions(LayoutContext c, int cssWidth) {
        if (!isDimensionsCalculated()) {
            CalculatedStyle style = getStyle();
            boolean borderBox = style.isBorderBox();

            RectPropertySet padding = getPadding(c);
            BorderPropertySet border = getBorder(c);

            if (cssWidth != -1 && !isAnonymous() &&
                    (getStyle().isIdent(CSSName.MARGIN_LEFT, IdentValue.AUTO) ||
                            getStyle().isIdent(CSSName.MARGIN_RIGHT, IdentValue.AUTO)) &&
                    getStyle().isNeedAutoMarginResolution()) {
                resolveAutoMargins(c, cssWidth, padding, border);
            }

            recalcMargin(c);
            RectPropertySet margin = getMargin(c);

            // CLEAN: cast to int
            setLeftMBP((int) margin.left() + (int) border.left() + (int) padding.left());
            setRightMBP((int) padding.right() + (int) border.right() + (int) margin.right());
            if (c.isPrint() && getStyle().isDynamicAutoWidth()) {
                setContentWidth(calcEffPageRelativeWidth(c));
            } else {
                int proporcionalContainingBlockWidth = cssWidth != -1 ? cssWidth : getContainingBlockWidth();

                if (borderBox) {
                    setContentWidth((proporcionalContainingBlockWidth - (int) border.width() - (int) padding.width()));
                } else {
                    setContentWidth((proporcionalContainingBlockWidth) - getLeftMBP() - getRightMBP());
                }
            }
            setHeight(0);

            if (!isAnonymous() || (isFromCaptionedTable() && isFloated())) {
                int pinnedContentWidth = -1;

                if (getStyle().isAbsolute() || getStyle().isFixed()) {
                    pinnedContentWidth = calcPinnedContentWidth(c);
                    if (pinnedContentWidth != -1) {
                        setContentWidth(pinnedContentWidth);
                    }
                }

                int cssHeight = getCSSHeight(c);
                if (cssHeight != -1) {
                    if (borderBox) {
                        setHeight(cssHeight - (int) padding.height() - (int) border.height());
                    } else {
                        setHeight(cssHeight);
                    }

                }

                //check if replaced
//                log.error(" check repl {}", getElement().getTagName());
                ReplacedElement re = getReplacedElement();
                if (re == null) {
                    re = c.getReplacedElementFactory().createReplacedElement(
                            c, this, c.getUac(), cssWidth, cssHeight);
                    if (re != null) {
                        re = fitReplacedElement(c, re);
                    }
                }
                if (re != null) {
                    setContentWidth(re.getIntrinsicWidth());
                    setHeight(re.getIntrinsicHeight());
                    setReplacedElement(re);
                } else if (cssWidth == -1 && pinnedContentWidth == -1 &&
                        style.isCanBeShrunkToFit()) {
                    setNeedShrinkToFitCalculatation(true);
                }

                if (!isReplaced()) {
                    applyCSSMinMaxWidth(c);
                }
            }

            setDimensionsCalculated(true);
        }
    }

    private void calcClearance(LayoutContext c) {
        if (getStyle().isCleared() && !getStyle().isFloated()) {
            c.translate(0, -getY());
            c.getBlockFormattingContext().clear(c, this);
            c.translate(0, getY());
            calcCanvasLocation();
        }
    }

    private void calcExtraPageClearance(LayoutContext c) {
        if (c.isPageBreaksAllowed() &&
                c.getExtraSpaceTop() > 0 && (getStyle().isSpecifiedAsBlock() || getStyle().isListItem())) {
            PageBox first = c.getRootLayer().getFirstPage(c, this);
            if (first != null && first.getTop() + c.getExtraSpaceTop() > getAbsY()) {
                int diff = first.getTop() + c.getExtraSpaceTop() - getAbsY();
                setY(getY() + diff);
                c.translate(0, diff);
                calcCanvasLocation();
            }
        }
    }

    private void addBoxID(LayoutContext c) {
        if (!isAnonymous()) {
            String name = c.getNamespaceHandler().getAnchorName(getElement());
            if (name != null) {
                c.addBoxId(name, this);
            }
            String id = c.getNamespaceHandler().getID(getElement());
            if (id != null) {
                c.addBoxId(id, this);
            }
        }
    }

    public void layout(LayoutContext c) {
        layout(c, 0);
    }

    public void layout(LayoutContext c, int contentStart) {
        CalculatedStyle style = getStyle();

        boolean pushedLayer = false;
        if (isRoot() || style.requiresLayer()) {
            pushedLayer = true;
            if (getLayer() == null) {
                c.pushLayer(this);
            } else {
                c.pushLayer(getLayer());
            }
        }

        if (style.isFixedBackground()) {
            c.getRootLayer().setFixedBackground(true);
        }

        calcClearance(c);

        if (isRoot() || getStyle().establishesBFC() || isMarginAreaRoot()) {
            BlockFormattingContext bfc = new BlockFormattingContext(this, c);
            c.pushBFC(bfc);
        }

        addBoxID(c);

        if (c.isPrint() && getStyle().isIdent(CSSName.FS_PAGE_SEQUENCE, IdentValue.START)) {
            c.getRootLayer().addPageSequence(this);
        }

        calcDimensions(c);
        calcShrinkToFitWidthIfNeeded(c);
        collapseMargins(c);

        calcExtraPageClearance(c);

        if (c.isPrint()) {
            PageBox firstPage = c.getRootLayer().getFirstPage(c, this);
            if (firstPage != null && firstPage.getTop() == getAbsY() - getPageClearance()) {
                resetTopMargin(c);
            }
        }

        BorderPropertySet border = getBorder(c);
        RectPropertySet margin = getMargin(c);
        RectPropertySet padding = getPadding(c);

        // save height in case fixed height
        int originalHeight = getHeight();

        if (!isReplaced()) {
            setHeight(0);
        }

        boolean didSetMarkerData = false;
        if (getStyle().isListItem()) {
            createMarkerData(c);
            c.setCurrentMarkerData(getMarkerData());
            didSetMarkerData = true;
        }

        // do children's layout
        int tx = (int) margin.left() + (int) border.left() + (int) padding.left();
        int ty = (int) margin.top() + (int) border.top() + (int) padding.top();
        setTx(tx);
        setTy(ty);
        c.translate(getTx(), getTy());
        if (!isReplaced())
            layoutChildren(c, contentStart);
        else {
            setState(DONE);
        }
        c.translate(-getTx(), -getTy());

        setChildrenHeight(getHeight());

        if (!isReplaced()) {
            if (!isAutoHeight()) {
                int delta = originalHeight - getHeight();
                if (delta > 0 || isAllowHeightToShrink()) {
                    setHeight(originalHeight);
                }
            }

            applyCSSMinMaxHeight(c);
        }

        if (isRoot() || getStyle().establishesBFC()) {
            if (getStyle().isAutoHeight()) {
                int delta =
                        c.getBlockFormattingContext().getFloatManager().getClearDelta(
                                c, getTy() + getHeight());
                if (delta > 0) {
                    setHeight(getHeight() + delta);
                    setChildrenHeight(getChildrenHeight() + delta);
                }
            }
        }

        if (didSetMarkerData) {
            c.setCurrentMarkerData(null);
        }

        calcLayoutHeight(c, border, margin, padding);

        if (isRoot() || getStyle().establishesBFC()) {
            c.popBFC();
        }

        if (pushedLayer) {
            c.popLayer();
        }
    }

    protected boolean isAllowHeightToShrink() {
        return true;
    }

    protected int getPageClearance() {
        return 0;
    }

    protected void calcLayoutHeight(
            LayoutContext c, BorderPropertySet border,
            RectPropertySet margin, RectPropertySet padding) {
        setHeight(getHeight() + ((int) margin.top() + (int) border.top() + (int) padding.top() +
                (int) padding.bottom() + (int) border.bottom() + (int) margin.bottom()));
        setChildrenHeight(getChildrenHeight() + ((int) margin.top() + (int) border.top() + (int) padding.top() +
                (int) padding.bottom() + (int) border.bottom() + (int) margin.bottom()));
    }


    private void calcShrinkToFitWidthIfNeeded(LayoutContext c) {
        if (isNeedShrinkToFitCalculatation()) {
            setContentWidth(calcShrinkToFitWidth(c) - getLeftMBP() - getRightMBP());
            applyCSSMinMaxWidth(c);
            setNeedShrinkToFitCalculatation(false);
        }
    }

    private void applyCSSMinMaxWidth(CssContext c) {
        if (!getStyle().isMaxWidthNone()) {
            int cssMaxWidth = getCSSMaxWidth(c);
            if (getContentWidth() > cssMaxWidth) {
                setContentWidth(cssMaxWidth);
            }
        }
        int cssMinWidth = getCSSMinWidth(c);
        if (cssMinWidth > 0 && getContentWidth() < cssMinWidth) {
            setContentWidth(cssMinWidth);
        }
    }

    private void applyCSSMinMaxHeight(CssContext c) {
        if (!getStyle().isMaxHeightNone()) {
            int cssMaxHeight = getCSSMaxHeight(c);
            if (getHeight() > cssMaxHeight) {
                setHeight(cssMaxHeight);
            }
        }
        int cssMinHeight = getCSSMinHeight(c);
        if (cssMinHeight > 0 && getHeight() < cssMinHeight) {
            setHeight(cssMinHeight);
        }
    }

    public void ensureChildren(LayoutContext c) {
        if (getChildrenContentType() == CONTENT_UNKNOWN) {
            BoxBuilder.createChildren(c, this);
        }
    }

    protected void layoutChildren(LayoutContext c, int contentStart) {
        setState(CHILDREN_FLUX);
        ensureChildren(c);

        if (getFirstLetterStyle() != null) {
            c.getFirstLettersTracker().addStyle(getFirstLetterStyle());
        }
        if (getFirstLineStyle() != null) {
            c.getFirstLinesTracker().addStyle(getFirstLineStyle());
        }

        switch (getChildrenContentType()) {
            case CONTENT_INLINE:
                layoutInlineChildren(c, contentStart, calcInitialBreakAtLine(c), true);
                break;
            case CONTENT_BLOCK:
                BlockBoxing.layoutContent(c, this, contentStart);
                break;
        }

        if (getFirstLetterStyle() != null) {
            c.getFirstLettersTracker().removeLast();
        }
        if (getFirstLineStyle() != null) {
            c.getFirstLinesTracker().removeLast();
        }

        setState(DONE);
    }

    protected void layoutInlineChildren(
            LayoutContext c, int contentStart, int breakAtLine, boolean tryAgain) {
        InlineBoxing.layoutContent(c, this, contentStart, breakAtLine);

        if (c.isPrint() && c.isPageBreaksAllowed() && getChildCount() > 1) {
            satisfyWidowsAndOrphans(c, contentStart, tryAgain);
        }

        if (tryAgain && getStyle().isTextJustify()) {
            justifyText();
        }
    }

    private void justifyText() {
        for (Iterator i = getChildIterator(); i.hasNext(); ) {
            LineBox line = (LineBox) i.next();
            line.justify();
        }
    }

    private void satisfyWidowsAndOrphans(LayoutContext c, int contentStart, boolean tryAgain) {
        LineBox firstLineBox = (LineBox) getChild(0);
        PageBox firstPage = c.getRootLayer().getFirstPage(c, firstLineBox);

        if (firstPage == null) {
            return;
        }

        int noContentLBs = 0;
        int i = 0;
        int cCount = getChildCount();
        while (i < cCount) {
            LineBox lB = (LineBox) getChild(i);
            if (lB.getAbsY() >= firstPage.getBottom()) {
                break;
            }
            if (!lB.isContainsContent()) {
                noContentLBs++;
            }
            i++;
        }

        if (i != cCount) {
            int orphans = (int) getStyle().asFloat(CSSName.ORPHANS);
            if (i - noContentLBs < orphans) {
                setNeedPageClear(true);
            } else {
                LineBox lastLineBox = (LineBox) getChild(cCount - 1);
                List pages = c.getRootLayer().getPages();
                PageBox lastPage = (PageBox) pages.get(firstPage.getPageNo() + 1);
                while (lastPage.getPageNo() != pages.size() - 1 &&
                        lastPage.getBottom() < lastLineBox.getAbsY()) {
                    lastPage = (PageBox) pages.get(lastPage.getPageNo() + 1);
                }

                noContentLBs = 0;
                i = cCount - 1;
                while (i >= 0 && ((LineBox) getChild(i)).getAbsY() >= lastPage.getTop()) {
                    LineBox lB = (LineBox) getChild(i);
                    if (lB.getAbsY() < lastPage.getTop()) {
                        break;
                    }
                    if (!lB.isContainsContent()) {
                        noContentLBs++;
                    }
                    i--;
                }

                int widows = (int) getStyle().asFloat(CSSName.WIDOWS);
                if (cCount - 1 - i - noContentLBs < widows) {
                    if (cCount - 1 - widows < orphans) {
                        setNeedPageClear(true);
                    } else if (tryAgain) {
                        int breakAtLine = cCount - 1 - widows;

                        resetChildren(c);
                        removeAllChildren();

                        layoutInlineChildren(c, contentStart, breakAtLine, false);
                    }
                }
            }
        }
    }

    public int getChildrenContentType() {
        return _childrenContentType;
    }

    public void setChildrenContentType(int contentType) {
        _childrenContentType = contentType;
    }

    public List getInlineContent() {
        return _inlineContent;
    }

    public void setInlineContent(List inlineContent) {
        _inlineContent = inlineContent;
        if (inlineContent != null) {
            for (Iterator i = inlineContent.iterator(); i.hasNext(); ) {
                Styleable child = (Styleable) i.next();
                if (child instanceof Box) {
                    ((Box) child).setContainingBlock(this);
                }
            }
        }
    }

    protected boolean isSkipWhenCollapsingMargins() {
        return false;
    }

    protected boolean isMayCollapseMarginsWithChildren() {
        return (!isRoot()) && getStyle().isMayCollapseMarginsWithChildren();
    }

    // This will require a rethink if we ever truly layout incrementally
    // Should only ever collapse top margin and pick up collapsable
    // bottom margins by looking back up the tree.
    private void collapseMargins(LayoutContext c) {
        if (!isTopMarginCalculated() || !isBottomMarginCalculated()) {
            recalcMargin(c);
            RectPropertySet margin = getMargin(c);

            if (!isTopMarginCalculated() && !isBottomMarginCalculated() && isVerticalMarginsAdjoin(c)) {
                MarginCollapseResult collapsedMargin =
                        _pendingCollapseCalculation != null ?
                                _pendingCollapseCalculation : new MarginCollapseResult();
                collapseEmptySubtreeMargins(c, collapsedMargin);
                setCollapsedBottomMargin(c, margin, collapsedMargin);
            } else {
                if (!isTopMarginCalculated()) {
                    MarginCollapseResult collapsedMargin =
                            _pendingCollapseCalculation != null ?
                                    _pendingCollapseCalculation : new MarginCollapseResult();

                    collapseTopMargin(c, true, collapsedMargin);
                    if ((int) margin.top() != collapsedMargin.getMargin()) {
                        setMarginTop(c, collapsedMargin.getMargin());
                    }
                }

                if (!isBottomMarginCalculated()) {
                    MarginCollapseResult collapsedMargin = new MarginCollapseResult();
                    collapseBottomMargin(c, true, collapsedMargin);

                    setCollapsedBottomMargin(c, margin, collapsedMargin);
                }
            }
        }
    }

    private void setCollapsedBottomMargin(LayoutContext c, RectPropertySet margin, MarginCollapseResult collapsedMargin) {
        BlockBox next = null;
        if (!isInline()) {
            next = getNextCollapsableSibling(collapsedMargin);
        }
        if (!(next == null || next instanceof AnonymousBlockBox) &&
                collapsedMargin.hasMargin()) {
            next._pendingCollapseCalculation = collapsedMargin;
            setMarginBottom(c, 0);
        } else if ((int) margin.bottom() != collapsedMargin.getMargin()) {
            setMarginBottom(c, collapsedMargin.getMargin());
        }
    }

    private BlockBox getNextCollapsableSibling(MarginCollapseResult collapsedMargin) {
        BlockBox next = (BlockBox) getNextSibling();
        while (next != null) {
            if (next instanceof AnonymousBlockBox) {
                ((AnonymousBlockBox) next).provideSiblingMarginToFloats(
                        collapsedMargin.getMargin());
            }
            if (!next.isSkipWhenCollapsingMargins()) {
                break;
            } else {
                next = (BlockBox) next.getNextSibling();
            }
        }
        return next;
    }

    private void collapseTopMargin(
            LayoutContext c, boolean calculationRoot, MarginCollapseResult result) {
        if (!isTopMarginCalculated()) {
            if (!isSkipWhenCollapsingMargins()) {
                calcDimensions(c);
                if (c.isPrint() && getStyle().isDynamicAutoWidthApplicable()) {
                    // Force recalculation once box is positioned
                    setDimensionsCalculated(false);
                }
                RectPropertySet margin = getMargin(c);
                result.update((int) margin.top());

                if (!calculationRoot && (int) margin.top() != 0) {
                    setMarginTop(c, 0);
                }

                if (isMayCollapseMarginsWithChildren() && isNoTopPaddingOrBorder(c)) {
                    ensureChildren(c);
                    if (getChildrenContentType() == CONTENT_BLOCK) {
                        for (Iterator i = getChildIterator(); i.hasNext(); ) {
                            BlockBox child = (BlockBox) i.next();
                            child.collapseTopMargin(c, false, result);

                            if (child.isSkipWhenCollapsingMargins()) {
                                continue;
                            }

                            break;
                        }
                    }
                }
            }

            setTopMarginCalculated(true);
        }
    }

    private void collapseBottomMargin(
            LayoutContext c, boolean calculationRoot, MarginCollapseResult result) {
        if (!isBottomMarginCalculated()) {
            if (!isSkipWhenCollapsingMargins()) {
                calcDimensions(c);
                if (c.isPrint() && getStyle().isDynamicAutoWidthApplicable()) {
                    // Force recalculation once box is positioned
                    setDimensionsCalculated(false);
                }
                RectPropertySet margin = getMargin(c);
                result.update((int) margin.bottom());

                if (!calculationRoot && (int) margin.bottom() != 0) {
                    setMarginBottom(c, 0);
                }

                if (isMayCollapseMarginsWithChildren() &&
                        !getStyle().isTable() && isNoBottomPaddingOrBorder(c)) {
                    ensureChildren(c);
                    if (getChildrenContentType() == CONTENT_BLOCK) {
                        for (int i = getChildCount() - 1; i >= 0; i--) {
                            BlockBox child = (BlockBox) getChild(i);

                            if (child.isSkipWhenCollapsingMargins()) {
                                continue;
                            }

                            child.collapseBottomMargin(c, false, result);

                            break;
                        }
                    }
                }
            }

            setBottomMarginCalculated(true);
        }
    }

    private boolean isNoTopPaddingOrBorder(LayoutContext c) {
        RectPropertySet padding = getPadding(c);
        BorderPropertySet border = getBorder(c);

        return (int) padding.top() == 0 && (int) border.top() == 0;
    }

    private boolean isNoBottomPaddingOrBorder(LayoutContext c) {
        RectPropertySet padding = getPadding(c);
        BorderPropertySet border = getBorder(c);

        return (int) padding.bottom() == 0 && (int) border.bottom() == 0;
    }

    private void collapseEmptySubtreeMargins(LayoutContext c, MarginCollapseResult result) {
        RectPropertySet margin = getMargin(c);
        result.update((int) margin.top());
        result.update((int) margin.bottom());

        setMarginTop(c, 0);
        setTopMarginCalculated(true);
        setMarginBottom(c, 0);
        setBottomMarginCalculated(true);

        ensureChildren(c);
        if (getChildrenContentType() == CONTENT_BLOCK) {
            for (Iterator i = getChildIterator(); i.hasNext(); ) {
                BlockBox child = (BlockBox) i.next();
                child.collapseEmptySubtreeMargins(c, result);
            }
        }
    }

    private boolean isVerticalMarginsAdjoin(LayoutContext c) {
        CalculatedStyle style = getStyle();

        BorderPropertySet borderWidth = style.getBorder(c);
        RectPropertySet padding = getPadding(c);

        boolean bordersOrPadding =
                (int) borderWidth.top() != 0 || (int) borderWidth.bottom() != 0 ||
                        (int) padding.top() != 0 || (int) padding.bottom() != 0;

        if (bordersOrPadding) {
            return false;
        }

        ensureChildren(c);
        if (getChildrenContentType() == CONTENT_INLINE) {
            return false;
        } else if (getChildrenContentType() == CONTENT_BLOCK) {
            for (Iterator i = getChildIterator(); i.hasNext(); ) {
                BlockBox child = (BlockBox) i.next();
                if (child.isSkipWhenCollapsingMargins() || !child.isVerticalMarginsAdjoin(c)) {
                    return false;
                }
            }
        }

        return style.asFloat(CSSName.MIN_HEIGHT) == 0 &&
                (isAutoHeight() || style.asFloat(CSSName.HEIGHT) == 0);
    }

    public boolean isTopMarginCalculated() {
        return _topMarginCalculated;
    }

    public void setTopMarginCalculated(boolean topMarginCalculated) {
        _topMarginCalculated = topMarginCalculated;
    }

    public boolean isBottomMarginCalculated() {
        return _bottomMarginCalculated;
    }

    public void setBottomMarginCalculated(boolean bottomMarginCalculated) {
        _bottomMarginCalculated = bottomMarginCalculated;
    }

    protected int getCSSWidth(CssContext c) {
        return getCSSWidth(c, false);
    }

    protected int getCSSWidth(CssContext c, boolean shrinkingToFit) {
        if (!isAnonymous()) {
            if (!getStyle().isAutoWidth()) {
                if (shrinkingToFit && !getStyle().isAbsoluteWidth()) {
                    return -1;
                } else {
                    int result = (int) getStyle().getFloatPropertyProportionalWidth(
                            CSSName.WIDTH, getContainingBlock().getContentWidth(), c);
                    return result >= 0 ? result : -1;
                }
            }
        }

        return -1;
    }

    protected int getCSSFitToWidth(CssContext c) {
        if (!isAnonymous()) {
            if (!getStyle().isIdent(CSSName.FS_FIT_IMAGES_TO_WIDTH, IdentValue.AUTO)) {
                int result = (int) getStyle().getFloatPropertyProportionalWidth(
                        CSSName.FS_FIT_IMAGES_TO_WIDTH, getContainingBlock().getContentWidth(), c);
                return result >= 0 ? result : -1;
            }
        }

        return -1;
    }

    protected int getCSSHeight(CssContext c) {
        if (!isAnonymous()) {
            if (!isAutoHeight()) {
                if (getStyle().hasAbsoluteUnit(CSSName.HEIGHT)) {
                    return (int) getStyle().getFloatPropertyProportionalHeight(CSSName.HEIGHT, 0, c);
                } else {
                    return (int) getStyle().getFloatPropertyProportionalHeight(
                            CSSName.HEIGHT,
                            ((BlockBox) getContainingBlock()).getCSSHeight(c),
                            c);
                }
            }
        }

        return -1;
    }

    public boolean isAutoHeight() {
        if (getStyle().isAutoHeight()) {
            return true;
        } else if (getStyle().hasAbsoluteUnit(CSSName.HEIGHT)) {
            return false;
        } else {
            // We have a percentage height, defer to our block parent (if applicable)
            Box cb = getContainingBlock();
            if (cb.isStyled() && (cb instanceof BlockBox)) {
                return ((BlockBox) cb).isAutoHeight();
            } else if (cb instanceof BlockBox && ((BlockBox) cb).isInitialContainingBlock()) {
                return false;
            } else {
                return true;
            }
        }
    }

    private int getCSSMinWidth(CssContext c) {
        return getStyle().getMinWidth(c, getContainingBlockWidth());
    }

    private int getCSSMaxWidth(CssContext c) {
        return getStyle().getMaxWidth(c, getContainingBlockWidth());
    }

    private int getCSSMinHeight(CssContext c) {
        return getStyle().getMinHeight(c, getContainingBlockCSSHeight(c));
    }

    private int getCSSMaxHeight(CssContext c) {
        return getStyle().getMaxHeight(c, getContainingBlockCSSHeight(c));
    }

    // Use only when the height of the containing block is required for
    // resolving percentage values.  Does not represent the actual (resolved) height
    // of the containing block.
    private int getContainingBlockCSSHeight(CssContext c) {
        if (!getContainingBlock().isStyled() ||
                getContainingBlock().getStyle().isAutoHeight()) {
            return 0;
        } else {
            if (getContainingBlock().getStyle().hasAbsoluteUnit(CSSName.HEIGHT)) {
                return (int) getContainingBlock().getStyle().getFloatPropertyProportionalTo(
                        CSSName.HEIGHT, 0, c);
            } else {
                return 0;
            }
        }
    }

    private int calcShrinkToFitWidth(LayoutContext c) {
        calcMinMaxWidth(c);

        return Math.min(Math.max(getMinWidth(), getAvailableWidth(c)), getMaxWidth());
    }

    protected int getAvailableWidth(LayoutContext c) {
        if (!getStyle().isAbsolute()) {
            return getContainingBlockWidth();
        } else {
            int left = 0;
            int right = 0;
            if (!getStyle().isIdent(CSSName.LEFT, IdentValue.AUTO)) {
                left =
                        (int) getStyle().getFloatPropertyProportionalTo(CSSName.LEFT,
                                getContainingBlock().getContentWidth(), c);
            }

            if (!getStyle().isIdent(CSSName.RIGHT, IdentValue.AUTO)) {
                right =
                        (int) getStyle().getFloatPropertyProportionalTo(CSSName.RIGHT,
                                getContainingBlock().getContentWidth(), c);
            }

            return getContainingBlock().getPaddingWidth(c) - left - right;
        }
    }

    protected boolean isFixedWidthAdvisoryOnly() {
        return false;
    }


    private void recalcMargin(LayoutContext c) {
        if (isTopMarginCalculated() && isBottomMarginCalculated()) {
            return;
        }

        // Check if we're a potential candidate upfront to avoid expensive
        // getStyleMargin(c, false) call
        FSDerivedValue topMargin = getStyle().valueByName(CSSName.MARGIN_TOP);
        boolean resetTop = topMargin instanceof LengthValue && !topMargin.hasAbsoluteUnit();

        FSDerivedValue bottomMargin = getStyle().valueByName(CSSName.MARGIN_BOTTOM);
        boolean resetBottom = bottomMargin instanceof LengthValue && !bottomMargin.hasAbsoluteUnit();

        if (!resetTop && !resetBottom) {
            return;
        }

        RectPropertySet styleMargin = getStyleMargin(c, false);
        RectPropertySet workingMargin = getMargin(c);

        // A shrink-to-fit calculation may have set incorrect values for
        // percentage margins (as the containing block width
        // hasn't been calculated yet).  Reset top and bottom margins
        // in this case.
        if (!isTopMarginCalculated() &&
                styleMargin.top() != workingMargin.top()) {
            setMarginTop(c, (int) styleMargin.top());
        }

        if (!isBottomMarginCalculated() &&
                styleMargin.bottom() != workingMargin.bottom()) {
            setMarginBottom(c, (int) styleMargin.bottom());
        }
    }

    public void calcMinMaxWidth(LayoutContext c) {
        if (!isMinMaxCalculated()) {
            RectPropertySet margin = getMargin(c);
            BorderPropertySet border = getBorder(c);
            RectPropertySet padding = getPadding(c);

            int width = getCSSWidth(c, true);

            if (width == -1) {
                if (isReplaced()) {
                    width = getReplacedElement().getIntrinsicWidth();
                } else {
                    int height = getCSSHeight(c);
                    ReplacedElement re = c.getReplacedElementFactory().createReplacedElement(
                            c, this, c.getUac(), width, height);
                    if (re != null) {
                        re = fitReplacedElement(c, re);
                        setReplacedElement(re);
                        width = getReplacedElement().getIntrinsicWidth();
                    }
                }
            }

            if (isReplaced() || (width != -1 && !isFixedWidthAdvisoryOnly())) {
                _minWidth = _maxWidth =
                        (int) margin.left() + (int) border.left() + (int) padding.left() +
                                width +
                                (int) margin.right() + (int) border.right() + (int) padding.right();
            } else {
                int cw = -1;
                if (width != -1) {
                    // Set a provisional content width on table cells so
                    // percentage values resolve correctly (but save and reset
                    // the existing value)
                    cw = getContentWidth();
                    setContentWidth(width);
                }

                _minWidth = _maxWidth =
                        (int) margin.left() + (int) border.left() + (int) padding.left() +
                                (int) margin.right() + (int) border.right() + (int) padding.right();

                int minimumMaxWidth = _maxWidth;
                if (width != -1) {
                    minimumMaxWidth += width;
                }

                ensureChildren(c);

                if (getChildrenContentType() == CONTENT_BLOCK ||
                        getChildrenContentType() == CONTENT_INLINE) {
                    switch (getChildrenContentType()) {
                        case CONTENT_BLOCK:
                            calcMinMaxWidthBlockChildren(c);
                            break;
                        case CONTENT_INLINE:
                            calcMinMaxWidthInlineChildren(c);
                            break;
                    }
                }

                if (minimumMaxWidth > _maxWidth) {
                    _maxWidth = minimumMaxWidth;
                }

                if (cw != -1) {
                    setContentWidth(cw);
                }
            }

            if (!isReplaced()) {
                calcMinMaxCSSMinMaxWidth(c, margin, border, padding);
            }

            setMinMaxCalculated(true);
        }
    }

    private ReplacedElement fitReplacedElement(LayoutContext c,
                                               ReplacedElement re) {
        int maxImageWidth = getCSSFitToWidth(c);
        if (maxImageWidth > -1 && re.getIntrinsicWidth() > maxImageWidth) {
            double oldWidth = (double) re.getIntrinsicWidth();
            double scale = ((double) maxImageWidth) / oldWidth;
            re = c.getReplacedElementFactory().createReplacedElement(
                    c, this, c.getUac(), maxImageWidth, (int) Math.rint(scale * (double) re.getIntrinsicHeight()));
        }
        return re;
    }

    private void calcMinMaxCSSMinMaxWidth(
            LayoutContext c, RectPropertySet margin, BorderPropertySet border,
            RectPropertySet padding) {
        int cssMinWidth = getCSSMinWidth(c);
        if (cssMinWidth > 0) {
            cssMinWidth +=
                    (int) margin.left() + (int) border.left() + (int) padding.left() +
                            (int) margin.right() + (int) border.right() + (int) padding.right();
            if (_minWidth < cssMinWidth) {
                _minWidth = cssMinWidth;
            }
        }
        if (!getStyle().isMaxWidthNone()) {
            int cssMaxWidth = getCSSMaxWidth(c);
            cssMaxWidth +=
                    (int) margin.left() + (int) border.left() + (int) padding.left() +
                            (int) margin.right() + (int) border.right() + (int) padding.right();
            if (_maxWidth > cssMaxWidth) {
                if (cssMaxWidth > _minWidth) {
                    _maxWidth = cssMaxWidth;
                } else {
                    _maxWidth = _minWidth;
                }
            }
        }
    }

    private void calcMinMaxWidthBlockChildren(LayoutContext c) {
        int childMinWidth = 0;
        int childMaxWidth = 0;

        for (Iterator i = getChildIterator(); i.hasNext(); ) {
            BlockBox child = (BlockBox) i.next();
            child.calcMinMaxWidth(c);
            if (child.getMinWidth() > childMinWidth) {
                childMinWidth = child.getMinWidth();
            }
            if (child.getMaxWidth() > childMaxWidth) {
                childMaxWidth = child.getMaxWidth();
            }
        }

        _minWidth += childMinWidth;
        _maxWidth += childMaxWidth;
    }

    private void calcMinMaxWidthInlineChildren(LayoutContext c) {
        int textIndent = (int) getStyle().getFloatPropertyProportionalWidth(
                CSSName.TEXT_INDENT, getContentWidth(), c);

        if (getStyle().isListItem() && getStyle().isListMarkerInside()) {
            createMarkerData(c);
            textIndent += getMarkerData().getLayoutWidth();
        }

        int childMinWidth = 0;
        int childMaxWidth = 0;
        int lineWidth = 0;

        InlineBox trimmableIB = null;

        for (Iterator i = _inlineContent.iterator(); i.hasNext(); ) {
            Styleable child = (Styleable) i.next();

            if (child.getStyle().isAbsolute() || child.getStyle().isFixed() || child.getStyle().isRunning()) {
                continue;
            }

            if (child.getStyle().isFloated() || child.getStyle().isInlineBlock() ||
                    child.getStyle().isInlineTable()) {
                if (child.getStyle().isFloated() && child.getStyle().isCleared()) {
                    if (trimmableIB != null) {
                        lineWidth -= trimmableIB.getTrailingSpaceWidth(c);
                    }
                    if (lineWidth > childMaxWidth) {
                        childMaxWidth = lineWidth;
                    }
                    lineWidth = 0;
                }
                trimmableIB = null;
                BlockBox block = (BlockBox) child;
                block.calcMinMaxWidth(c);
                lineWidth += block.getMaxWidth();
                if (block.getMinWidth() > childMinWidth) {
                    childMinWidth = block.getMinWidth();
                }
            } else { /* child.getStyle().isInline() */
                InlineBox iB = (InlineBox) child;
                IdentValue whitespace = iB.getStyle().getWhitespace();
                iB.calcMinMaxWidth(c, getContentWidth(), lineWidth == 0);

                if (whitespace == IdentValue.NOWRAP) {
                    lineWidth += textIndent + iB.getMaxWidth();
                    if (iB.getMinWidth() > childMinWidth) {
                        childMinWidth = iB.getMinWidth();
                    }
                    trimmableIB = iB;
                } else if (whitespace == IdentValue.PRE) {
                    if (trimmableIB != null) {
                        lineWidth -= trimmableIB.getTrailingSpaceWidth(c);
                    }
                    trimmableIB = null;
                    if (lineWidth > childMaxWidth) {
                        childMaxWidth = lineWidth;
                    }
                    lineWidth = textIndent + iB.getFirstLineWidth();
                    if (lineWidth > childMinWidth) {
                        childMinWidth = lineWidth;
                    }
                    lineWidth = iB.getMaxWidth();
                    if (lineWidth > childMinWidth) {
                        childMinWidth = lineWidth;
                    }
                    if (childMinWidth > childMaxWidth) {
                        childMaxWidth = childMinWidth;
                    }
                    lineWidth = 0;
                } else if (whitespace == IdentValue.PRE_WRAP || whitespace == IdentValue.PRE_LINE) {
                    lineWidth += textIndent + iB.getFirstLineWidth();
                    if (trimmableIB != null) {
                        lineWidth -= trimmableIB.getTrailingSpaceWidth(c);
                    }
                    if (lineWidth > childMaxWidth) {
                        childMaxWidth = lineWidth;
                    }

                    if (iB.getMaxWidth() > childMaxWidth) {
                        childMaxWidth = iB.getMaxWidth();
                    }
                    if (iB.getMinWidth() > childMinWidth) {
                        childMinWidth = iB.getMinWidth();
                    }
                    if (whitespace == IdentValue.PRE_LINE) {
                        trimmableIB = iB;
                    } else {
                        trimmableIB = null;
                    }
                    lineWidth = 0;
                } else /* if (whitespace == IdentValue.NORMAL) */ {
                    lineWidth += textIndent + iB.getMaxWidth();
                    if (iB.getMinWidth() > childMinWidth) {
                        childMinWidth = textIndent + iB.getMinWidth();
                    }
                    trimmableIB = iB;
                }

                if (textIndent > 0) {
                    textIndent = 0;
                }
            }
        }

        if (trimmableIB != null) {
            lineWidth -= trimmableIB.getTrailingSpaceWidth(c);
        }
        if (lineWidth > childMaxWidth) {
            childMaxWidth = lineWidth;
        }

        _minWidth += childMinWidth;
        _maxWidth += childMaxWidth;
    }

    public int getMaxWidth() {
        return _maxWidth;
    }

    protected void setMaxWidth(int maxWidth) {
        _maxWidth = maxWidth;
    }

    public int getMinWidth() {
        return _minWidth;
    }

    protected void setMinWidth(int minWidth) {
        _minWidth = minWidth;
    }

    public void styleText(LayoutContext c) {
        styleText(c, getStyle());
    }

    // FIXME Should be expanded into generic restyle facility
    public void styleText(LayoutContext c, CalculatedStyle style) {
        if (getChildrenContentType() == CONTENT_INLINE) {
            LinkedList styles = new LinkedList();
            styles.add(style);
            for (Iterator i = _inlineContent.iterator(); i.hasNext(); ) {
                Styleable child = (Styleable) i.next();
                if (child instanceof InlineBox) {
                    InlineBox iB = (InlineBox) child;

                    if (iB.isStartsHere()) {
                        CascadedStyle cs = null;
                        if (iB.getElement() != null) {
                            if (iB.getPseudoElementOrClass() == null) {
                                cs = c.getCss().getCascadedStyle(iB.getElement(), false);
                            } else {
                                cs = c.getCss().getPseudoElementStyle(
                                        iB.getElement(), iB.getPseudoElementOrClass());
                            }
                            styles.add(((CalculatedStyle) styles.getLast()).deriveStyle(cs));
                        } else {
                            styles.add(style.createAnonymousStyle(IdentValue.INLINE));
                        }
                    }

                    iB.setStyle(((CalculatedStyle) styles.getLast()));
                    iB.applyTextTransform();

                    if (iB.isEndsHere()) {
                        styles.removeLast();
                    }
                }
            }
        }
    }

    protected void calcChildPaintingInfo(
            final CssContext c, final PaintingInfo result, final boolean useCache) {
        if (getPersistentBFC() != null) {
            (this).getPersistentBFC().getFloatManager().performFloatOperation(
                    new FloatManager.FloatOperation() {
                        public void operate(Box floater) {
                            PaintingInfo info = floater.calcPaintingInfo(c, useCache);
                            moveIfGreater(
                                    result.getOuterMarginCorner(),
                                    info.getOuterMarginCorner());
                        }
                    });
        }
        super.calcChildPaintingInfo(c, result, useCache);
    }

    public CascadedStyle getFirstLetterStyle() {
        return _firstLetterStyle;
    }

    public void setFirstLetterStyle(CascadedStyle firstLetterStyle) {
        _firstLetterStyle = firstLetterStyle;
    }

    public CascadedStyle getFirstLineStyle() {
        return _firstLineStyle;
    }

    public void setFirstLineStyle(CascadedStyle firstLineStyle) {
        _firstLineStyle = firstLineStyle;
    }

    protected boolean isMinMaxCalculated() {
        return _minMaxCalculated;
    }

    protected void setMinMaxCalculated(boolean minMaxCalculated) {
        _minMaxCalculated = minMaxCalculated;
    }

    protected void setDimensionsCalculated(boolean dimensionsCalculated) {
        _dimensionsCalculated = dimensionsCalculated;
    }

    private boolean isDimensionsCalculated() {
        return _dimensionsCalculated;
    }

    protected void setNeedShrinkToFitCalculatation(boolean needShrinkToFitCalculatation) {
        _needShrinkToFitCalculatation = needShrinkToFitCalculatation;
    }

    private boolean isNeedShrinkToFitCalculatation() {
        return _needShrinkToFitCalculatation;
    }

    public void initStaticPos(LayoutContext c, BlockBox parent, int childOffset) {
        setX(0);
        setY(childOffset);
    }

    public int calcBaseline(LayoutContext c) {
        for (int i = 0; i < getChildCount(); i++) {
            Box b = getChild(i);
            if (b instanceof LineBox) {
                return b.getAbsY() + ((LineBox) b).getBaseline();
            } else {
                if (b instanceof TableRowBox) {
                    return b.getAbsY() + ((TableRowBox) b).getBaseline();
                } else {
                    int result = ((BlockBox) b).calcBaseline(c);
                    if (result != NO_BASELINE) {
                        return result;
                    }
                }
            }
        }

        return NO_BASELINE;
    }

    protected int calcInitialBreakAtLine(LayoutContext c) {
        BreakAtLineContext bContext = c.getBreakAtLineContext();
        if (bContext != null && bContext.getBlock() == this) {
            return bContext.getLine();
        }
        return 0;
    }

    public boolean isCurrentBreakAtLineContext(LayoutContext c) {
        BreakAtLineContext bContext = c.getBreakAtLineContext();
        return bContext != null && bContext.getBlock() == this;
    }

    public BreakAtLineContext calcBreakAtLineContext(LayoutContext c) {
        if (!c.isPrint() || !getStyle().isKeepWithInline()) {
            return null;
        }

        LineBox breakLine = findLastNthLineBox((int) getStyle().asFloat(CSSName.WIDOWS));
        if (breakLine != null) {
            PageBox linePage = c.getRootLayer().getLastPage(c, breakLine);
            PageBox ourPage = c.getRootLayer().getLastPage(c, this);
            if (linePage != null && ourPage != null && linePage.getPageNo() + 1 == ourPage.getPageNo()) {
                BlockBox breakBox = (BlockBox) breakLine.getParent();
                return new BreakAtLineContext(breakBox, breakBox.findOffset(breakLine));
            }
        }

        return null;
    }

    public int calcInlineBaseline(CssContext c) {
        if (isReplaced() && getReplacedElement().hasBaseline()) {
            Rectangle bounds = getContentAreaEdge(getAbsX(), getAbsY(), c);
            return bounds.y + getReplacedElement().getBaseline() - getAbsY();
        } else {
            LineBox lastLine = findLastLineBox();
            if (lastLine == null) {
                return getHeight();
            } else {
                return lastLine.getAbsY() + lastLine.getBaseline() - getAbsY();
            }
        }
    }

    public int findOffset(Box box) {
        int ccount = getChildCount();
        for (int i = 0; i < ccount; i++) {
            if (getChild(i) == box) {
                return i;
            }
        }
        return -1;
    }

    public LineBox findLastNthLineBox(int count) {
        LastLineBoxContext context = new LastLineBoxContext(count);
        findLastLineBox(context);
        return context.line;
    }

    private static class LastLineBoxContext {
        public int current;
        public LineBox line;

        public LastLineBoxContext(int i) {
            this.current = i;
        }
    }

    private void findLastLineBox(LastLineBoxContext context) {
        int type = getChildrenContentType();
        int ccount = getChildCount();
        if (ccount > 0) {
            if (type == CONTENT_INLINE) {
                for (int i = ccount - 1; i >= 0; i--) {
                    LineBox child = (LineBox) getChild(i);
                    if (child.getHeight() > 0) {
                        context.line = child;
                        if (--context.current == 0) {
                            return;
                        }
                    }
                }
            } else if (type == CONTENT_BLOCK) {
                for (int i = ccount - 1; i >= 0; i--) {
                    ((BlockBox) getChild(i)).findLastLineBox(context);
                    if (context.current == 0) {
                        break;
                    }
                }
            }
        }
    }

    private LineBox findLastLineBox() {
        int type = getChildrenContentType();
        int ccount = getChildCount();
        if (ccount > 0) {
            if (type == CONTENT_INLINE) {
                for (int i = ccount - 1; i >= 0; i--) {
                    LineBox result = (LineBox) getChild(i);
                    if (result.getHeight() > 0) {
                        return result;
                    }
                }
            } else if (type == CONTENT_BLOCK) {
                for (int i = ccount - 1; i >= 0; i--) {
                    LineBox result = ((BlockBox) getChild(i)).findLastLineBox();
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    private LineBox findFirstLineBox() {
        int type = getChildrenContentType();
        int ccount = getChildCount();
        if (ccount > 0) {
            if (type == CONTENT_INLINE) {
                for (int i = 0; i < ccount; i++) {
                    LineBox result = (LineBox) getChild(i);
                    if (result.getHeight() > 0) {
                        return result;
                    }
                }
            } else if (type == CONTENT_BLOCK) {
                for (int i = 0; i < ccount; i++) {
                    LineBox result = ((BlockBox) getChild(i)).findFirstLineBox();
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    public boolean isNeedsKeepWithInline(LayoutContext c) {
        if (c.isPrint() && getStyle().isKeepWithInline()) {
            LineBox line = findFirstLineBox();
            if (line != null) {
                PageBox linePage = c.getRootLayer().getFirstPage(c, line);
                PageBox ourPage = c.getRootLayer().getFirstPage(c, this);
                return linePage != null && ourPage != null && linePage.getPageNo() == ourPage.getPageNo() + 1;
            }
        }

        return false;
    }

    public boolean isFloated() {
        return _floatedBoxData != null;
    }

    public FloatedBoxData getFloatedBoxData() {
        return _floatedBoxData;
    }

    public void setFloatedBoxData(FloatedBoxData floatedBoxData) {
        _floatedBoxData = floatedBoxData;
    }

    public int getChildrenHeight() {
        return _childrenHeight;
    }

    protected void setChildrenHeight(int childrenHeight) {
        _childrenHeight = childrenHeight;
    }

    public boolean isFromCaptionedTable() {
        return _fromCaptionedTable;
    }

    public void setFromCaptionedTable(boolean fromTable) {
        _fromCaptionedTable = fromTable;
    }

    protected boolean isInlineBlock() {
        return isInline();
    }

    public boolean isInMainFlow() {
        Box flowRoot = this;
        while (flowRoot.getParent() != null) {
            flowRoot = flowRoot.getParent();
        }

        return flowRoot.isRoot();
    }

    public Box getDocumentParent() {
        Box staticEquivalent = getStaticEquivalent();
        if (staticEquivalent != null) {
            return staticEquivalent;
        } else {
            return getParent();
        }
    }

    public boolean isContainsInlineContent(LayoutContext c) {
        ensureChildren(c);
        switch (getChildrenContentType()) {
            case CONTENT_INLINE:
                return true;
            case CONTENT_EMPTY:
                return false;
            case CONTENT_BLOCK:
                for (Iterator i = getChildIterator(); i.hasNext(); ) {
                    BlockBox box = (BlockBox) i.next();
                    if (box.isContainsInlineContent(c)) {
                        return true;
                    }
                }
                return false;
        }

        throw new RuntimeException("internal error: no children");
    }

    public boolean checkPageContext(LayoutContext c) {
        if (!getStyle().isIdent(CSSName.PAGE, IdentValue.AUTO)) {
            String pageName = getStyle().getStringProperty(CSSName.PAGE);
            if ((!pageName.equals(c.getPageName())) && isInDocumentFlow() &&
                    isContainsInlineContent(c)) {
                c.setPendingPageName(pageName);
                return true;
            }
        } else if (c.getPageName() != null && isInDocumentFlow()) {
            c.setPendingPageName(null);
            return true;
        }

        return false;
    }

    public boolean isNeedsClipOnPaint(RenderingContext c) {
        return !isReplaced() &&
                getStyle().isIdent(CSSName.OVERFLOW, IdentValue.HIDDEN) &&
                getStyle().isOverflowApplies();
    }


    protected void propagateExtraSpace(
            LayoutContext c,
            ContentLimitContainer parentContainer, ContentLimitContainer currentContainer,
            int extraTop, int extraBottom) {
        int start = currentContainer.getInitialPageNo();
        int end = currentContainer.getLastPageNo();
        int current = start;

        while (current <= end) {
            ContentLimit contentLimit =
                    currentContainer.getContentLimit(current);

            if (current != start) {
                int top = contentLimit.getTop();
                if (top != ContentLimit.UNDEFINED) {
                    parentContainer.updateTop(c, top - extraTop);
                }
            }

            if (current != end) {
                int bottom = contentLimit.getBottom();
                if (bottom != ContentLimit.UNDEFINED) {
                    parentContainer.updateBottom(c, bottom + extraBottom);
                }
            }

            current++;
        }
    }

    private static class MarginCollapseResult {
        private int maxPositive;
        private int maxNegative;

        public void update(int value) {
            if (value < 0 && value < maxNegative) {
                maxNegative = value;
            }

            if (value > 0 && value > maxPositive) {
                maxPositive = value;
            }
        }

        public int getMargin() {
            return maxPositive + maxNegative;
        }

        public boolean hasMargin() {
            return maxPositive != 0 || maxNegative != 0;
        }
    }
}
