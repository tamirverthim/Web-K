package org.xhtmlrenderer.js.impl;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSDeclaration;
import com.helger.css.decl.shorthand.CSSShortHandDescriptor;
import com.helger.css.decl.shorthand.CSSShortHandRegistry;
import com.helger.css.property.ECSSProperty;
import com.helger.css.reader.CSSReaderDeclarationList;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.js.geom.DOMMatrix;
import org.xhtmlrenderer.js.geom.DOMMatrix2DInit;
import org.xhtmlrenderer.js.html5.ImageSmoothingQuality;
import org.xhtmlrenderer.js.html5.canvas.*;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Sequence;
import org.xhtmlrenderer.js.whatwg_dom.Element;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CanvasRenderingContext2DImpl implements CanvasRenderingContext2D {

    int width;
    int height;

    Object fillStyle;
    Object strokeStyle;

    Graphics2D g2d;
    BufferedImage image;

    LinkedList<G2DState> stateStack = new LinkedList<>();

    java.awt.geom.Path2D path2D = new java.awt.geom.Path2D.Double();

    // G2D state is not equal to state in stack
    boolean stateDirty;

    HTMLCanvasElementImpl canvas;
    boolean wasFill;
    DOMString fontStyle;
    
    // region external WebIDL attributes implementations
    
    Attribute<Double> globalAlpha = new Attribute<Double>() {
        @Override
        public Double get() {
            return state().getAlpha();
        }

        @Override
        public void set(Double aDouble) {
            state().setAlpha(aDouble);
            stateDirty = true;
        }
    };
    
    // endregion

    public CanvasRenderingContext2DImpl(HTMLCanvasElementImpl canvas, int width, int height) {
        this.canvas = canvas;
        resize(width, height);
    }

    @Override
    public HTMLCanvasElement canvas() {
        return canvas;
    }

    @Override
    public Attribute<Double> globalAlpha() {
        return globalAlpha;
    }

    @Override
    public Attribute<DOMString> globalCompositeOperation() {
        return null;
    }

    @Override
    public void drawImage(CanvasImageSource image, double dx, double dy) {

    }

    @Override
    public void drawImage(CanvasImageSource image, double dx, double dy, double dw, double dh) {

    }

    @Override
    public void drawImage(CanvasImageSource image, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh) {

    }

    @Override
    public void beginPath() {
        path2D = new java.awt.geom.Path2D.Double();
    }

    @Override
    public void fill(CanvasFillRule fillRule) {
        ensureState(true);
        g2d.fill(path2D);
    }

//    @Override
//    public void fill(Path2D path, CanvasFillRule fillRule) {
//
//    }

    @Override
    public void stroke() {
        ensureState(false);
        g2d.draw(path2D);
    }

    @Override
    public void clip(CanvasFillRule fillRule) {

    }

    @Override
    public void clip(Path2D path, CanvasFillRule fillRule) {

    }

    @Override
    public void resetClip() {

    }

    @Override
    public boolean isPointInPath(double x, double y, CanvasFillRule fillRule) {
        return false;
    }

    @Override
    public boolean isPointInPath(Path2D path, double x, double y, CanvasFillRule fillRule) {
        return false;
    }

    @Override
    public boolean isPointInStroke(double x, double y) {
        return false;
    }

    @Override
    public boolean isPointInStroke(Path2D path, double x, double y) {
        return false;
    }

    @Override
    public Attribute<Object> strokeStyle() {
        return Attribute.receive((value) -> {
            CanvasRenderingContext2DImpl.this.strokeStyle = value;
            if (value instanceof DOMString) {

                try {
                    val color = Color.decode(value.toString());
                    state().setStrokeColor(color);
                    stateDirty = true;
                } catch (NumberFormatException e) {
                    state().setFillColor(Color.blue);
                }
                

            } else {
                // todo grad and pattern
                throw new RuntimeException();
            }
        }).give(() -> fillStyle);
    }

    @Override
    public Attribute<Object> fillStyle() {
        return Attribute.receive((value) -> {
            CanvasRenderingContext2DImpl.this.fillStyle = value;
            if (value instanceof DOMString) {

                try {
                    val color = Color.decode(value.toString());
                    state().setFillColor(color);
                    stateDirty = true;
                } catch (NumberFormatException e) {
                    state().setFillColor(Color.red);
                }

            } else {
                // todo grad and pattern
                throw new RuntimeException();
            }
        }).give(() -> fillStyle);
    }

    @Override
    public CanvasGradient createLinearGradient(double x0, double y0, double x1, double y1) {
        return null;
    }

    @Override
    public CanvasGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1) {
        return null;
    }

    @Override
    public CanvasPattern createPattern(CanvasImageSource image, DOMString repetition) {
        return null;
    }

    @Override
    public Attribute<DOMString> filter() {
        return null;
    }

    @Override
    public ImageData createImageData(long sw, long sh) {
        return null;
    }

    @Override
    public ImageData createImageData(ImageData imagedata) {
        return null;
    }

    @Override
    public ImageData getImageData(long sx, long sy, long sw, long sh) {
        return null;
    }

    @Override
    public void putImageData(ImageData imagedata, long dx, long dy) {

    }

    @Override
    public void putImageData(ImageData imagedata, long dx, long dy, long dirtyX, long dirtyY, long dirtyWidth, long dirtyHeight) {

    }

    @Override
    public Attribute<Boolean> imageSmoothingEnabled() {
        return null;
    }

    @Override
    public Attribute<ImageSmoothingQuality> imageSmoothingQuality() {
        return null;
    }

    @Override
    public void closePath() {
        path2D.closePath();
    }

    @Override
    public void moveTo(double x, double y) {
        path2D.moveTo(x, y);
    }

    @Override
    public void lineTo(double x, double y) {
        path2D.lineTo(x, y);
    }

    @Override
    public void quadraticCurveTo(double cpx, double cpy, double x, double y) {
        path2D.quadTo(cpx, cpy, x, y);
    }

    @Override
    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {
        path2D.curveTo(cp1x, cp1y, cp2x, cp2y, x, y);
    }

    @Override
    public void arcTo(double x1, double y1, double x2, double y2, double radius) {

    }

    @Override
    public void rect(double x, double y, double w, double h) {
        path2D.append(new Rectangle2D.Double(x, y, w, h), false);
    }

    //  boolean ccw = arcAngle > 0;
    //    ctx.beginPath();
    //    ctx.arc(cx, cy, r, -startAngle, -(startAngle + arcAngle), ccw);
    //    ctx.stroke();

    // x = -startAngle - arcAngle

    // arcA = - startAngle - x

    //  int top = (int) (cx - r);
    //    int left = (int) (cy - r);
    //    int diam = (int) (2*r);
    //    currentState().prepareStroke(g2d);
    //    g2d.drawArc(top, left, diam, diam,
    //                FloatMath.round(FloatMath.toDegrees(startAngle)),
    //                FloatMath.round(FloatMath.toDegrees(arcAngle)));

    @Override
    public void arc(double x, double y, double r, double startAngle, double endAngle, boolean anticlockwise) {
        double top = x - r;
        double left = y - r;
        double diam = 2 * r;
        ensureState(false);

        val ext = Math.abs(startAngle - endAngle);

        path2D.append(
                new Arc2D.Double(top, left, diam, diam,
                        Math.toDegrees(anticlockwise ? -startAngle : endAngle),
                        Math.toDegrees(anticlockwise ? -endAngle : endAngle), Arc2D.OPEN), false);
    }

    @Override
    public void ellipse(double x, double y, double radiusX, double radiusY, double rotation, double startAngle, double endAngle, boolean anticlockwise) {

    }

    @Override
    public Attribute<Double> lineWidth() {
        return Attribute.<Double>receive(value ->
                state().setLineWidth(value)).give(() -> state().getLineWidth());
    }

    @Override
    public Attribute<CanvasLineCap> lineCap() {
        return new Attribute<CanvasLineCap>() {
            @Override
            public CanvasLineCap get() {
                return CanvasLineCap.butt;
            }

            @Override
            public void set(CanvasLineCap canvasLineCap) {

            }
        };
    }

    @Override
    public Attribute<CanvasLineJoin> lineJoin() {
        return new Attribute<CanvasLineJoin>() {
            @Override
            public CanvasLineJoin get() {
                return CanvasLineJoin.miter;
            }

            @Override
            public void set(CanvasLineJoin canvasLineJoin) {

            }
        };
    }

    @Override
    public Attribute<Double> miterLimit() {
        return null;
    }

    @Override
    public void setLineDash(Sequence<Double> segments) {

    }

    @Override
    public Sequence<Double> getLineDash() {
        return null;
    }

    @Override
    public Attribute<Double> lineDashOffset() {
        return new Attribute<Double>() {
            @Override
            public Double get() {
                return null;
            }

            @Override
            public void set(Double aDouble) {

            }
        };
    }

    @Override
    public void clearRect(double x, double y, double w, double h) {
        g2d.clearRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void fillRect(double x, double y, double w, double h) {
        ensureState(true);
        g2d.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void strokeRect(double x, double y, double w, double h) {
        ensureState(false);
        g2d.drawRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public Attribute<Double> shadowOffsetX() {
        return null;
    }

    @Override
    public Attribute<Double> shadowOffsetY() {
        return null;
    }

    @Override
    public Attribute<Double> shadowBlur() {
        return null;
    }

    @Override
    public Attribute<DOMString> shadowColor() {
        return null;
    }

    @Override
    public void save() {
        stateStack.add(state().clone());
    }

    @Override
    public void restore() {
        stateStack.pop();
        stateDirty = true;
    }

    @Override
    public void fillText(DOMString text, double x, double y, Double maxWidth) {
        ensureState(true);
        g2d.drawString(text.toString(), (int) x, (int) y);
        // todo handle max width
    }

    @Override
    public void strokeText(DOMString text, double x, double y, Double maxWidth) {

    }

    @Override
    public TextMetrics measureText(DOMString text) {
        ensureState();
        return new TextMetricsImpl(g2d.getFontMetrics().stringWidth(text.toString()));
    }

    @Override
    public Attribute<DOMString> font() {
        return Attribute.<DOMString>receive(style -> {
            fontStyle = style;
            val styleString = style.toString();
            val declarations = CSSReaderDeclarationList.readFromString("font: " + styleString, ECSSVersion.CSS30);
            val declaration = declarations.getDeclarationAtIndex(0);

            // Get the Shorthand descriptor for "border"    
            final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor(ECSSProperty.FONT);

            // And now split it into pieces
            final List<CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces(declaration);
            for (CSSDeclaration aSplittedDecl : aSplittedDecls) {

                val fontSize = aSplittedDecl.getExpression().getAsCSSString();
                if (fontSize.endsWith("px")) {
                    state().setFontSize((int) Math.round(Double.parseDouble(fontSize.substring(0, 2))));
                    stateDirty = true;
                }

            }

        }).give(() -> fontStyle);
    }

    @Override
    public Attribute<CanvasTextAlign> textAlign() {
        return new Attribute<CanvasTextAlign>() {
            @Override
            public CanvasTextAlign get() {
                return null;
            }

            @Override
            public void set(CanvasTextAlign canvasTextAlign) {

            }
        };
    }

    @Override
    public Attribute<CanvasTextBaseline> textBaseline() {
        
        return new Attribute<CanvasTextBaseline>() {
            @Override
            public CanvasTextBaseline get() {
                return null;
            }

            @Override
            public void set(CanvasTextBaseline canvasTextBaseline) {

            }
        };
    }

    @Override
    public Attribute<CanvasDirection> direction() {
        return null;
    }

    @Override
    public void scale(double x, double y) {
        state().scale(x, y);
        stateDirty = true;
    }

    @Override
    public void rotate(double angle) {
        state().rotate(angle);
        stateDirty = true;
    }

    @Override
    public void translate(double x, double y) {
        state().translate(x, y);
        stateDirty = true;
    }

    @Override
    public void transform(double a, double b, double c, double d, double e, double f) {
        state().transform(a, b, c, d, e, f);
        stateDirty = true;
    }

    @Override
    public DOMMatrix getTransform() {
        return null;
    }

    @Override
    public void setTransform(double a, double b, double c, double d, double e, double f) {
        state().setTransform(a, b, c, d, e, f);
        stateDirty = true;
    }

    @Override
    public void setTransform(DOMMatrix2DInit transform) {
        
    }

    @Override
    public void resetTransform() {
        state().setTransform(1, 0,0,1,0,0);
        stateDirty = true;
    }

    @Override
    public void drawFocusIfNeeded(Element element) {
        
    }

    @Override
    public void drawFocusIfNeeded(Path2D path, Element element) {

    }

    @Override
    public void scrollPathIntoView() {

    }

    @Override
    public void scrollPathIntoView(Path2D path) {

    }

    // region custom

    void init() {

        // initial state
        stateStack = new LinkedList<>();
        stateStack.push(new G2DState());
        stateDirty = false;

        if (width == 0 || height == 0) {
            return;
        }

        image = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleImage(width, height);
        g2d = (Graphics2D) image.getGraphics();
        g2d.setBackground(Color.red);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, width, height);
    }


    public void resize(int w, int h) {
        if (w == this.width && h == this.height) {
            return;
        }
        this.width = w;
        this.height = h;
        init();
    }

    private G2DState state() {
        return stateStack.getLast();
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void ensureState(boolean fill) {
        if (stateDirty || wasFill != fill) {
            state().apply(g2d, fill);
            wasFill = fill;
            stateDirty = false;
        }
    }

    private void ensureState() {
        if (stateDirty) {
            state().apply(g2d);
            stateDirty = false;
        }
    }


    // endregion
}
