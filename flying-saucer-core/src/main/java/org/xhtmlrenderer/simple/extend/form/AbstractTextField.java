/*
 * {{{ header & license
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
package org.xhtmlrenderer.simple.extend.form;

import javax.swing.JComponent;

import org.xhtmlrenderer.dom.nodes.Element;
import org.xhtmlrenderer.css.constants.CSSName;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.css.style.FSDerivedValue;
import org.xhtmlrenderer.css.style.derived.LengthValue;
import org.xhtmlrenderer.css.style.derived.RectPropertySet;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.XhtmlForm;

abstract class AbstractTextField extends InputField {

    public AbstractTextField(Element e, XhtmlForm form, LayoutContext context, BlockBox box)
    {
        super(e, form, context, box);
    }

    @Override
    protected void applyComponentStyle(JComponent component)
    {
        CalculatedStyle style = getBox().getStyle();
        RectPropertySet padding = style.getCachedPadding();

        Integer paddingTop = getLengthValue(style, CSSName.PADDING_TOP);
        Integer paddingLeft = getLengthValue(style, CSSName.PADDING_LEFT);
        Integer paddingBottom = getLengthValue(style, CSSName.PADDING_BOTTOM);
        Integer paddingRight = getLengthValue(style, CSSName.PADDING_RIGHT);


        int top = paddingTop == null ? 2 : Math.max(2, paddingTop.intValue());
        int left = paddingLeft == null ? 3 : Math.max(3, paddingLeft.intValue());
        int bottom = paddingBottom == null ? 2 : Math.max(2, paddingBottom.intValue());
        int right = paddingRight == null ? 3 : Math.max(3, paddingRight.intValue());

        padding.setRight(0);
        padding.setLeft(0);
        padding.setTop(0);
        padding.setBottom(0);

        FSDerivedValue widthValue = style.valueByName(CSSName.WIDTH);
        if (widthValue instanceof LengthValue)
        {
            intrinsicWidth = new Integer(getBox().getContentWidth() + left + right);
        }

        FSDerivedValue heightValue = style.valueByName(CSSName.HEIGHT);
        if (heightValue instanceof LengthValue)
        {
            intrinsicHeight = new Integer(getBox().getHeight() + top + bottom);
        }
    }

}
