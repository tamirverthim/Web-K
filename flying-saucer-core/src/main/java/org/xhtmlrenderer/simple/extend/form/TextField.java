/*
 * {{{ header & license
 * Copyright (c) 2007 Sean Bright
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
import javax.swing.JTextField;

import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.XhtmlForm;
import org.xhtmlrenderer.util.XHTMLUtils;

class TextField extends AbstractTextField {

    public TextField(org.xhtmlrenderer.dom.nodes.Element e, XhtmlForm form, LayoutContext context, BlockBox box)
    {
        super(e, form, context, box);
    }

    public JComponent create()
    {
        JTextField textfield = SwingComponentFactory.getInstance().createTextField(this);
        textfield.setColumns(XHTMLUtils.getIntValue(getElement(), "size", 15));

        XHTMLUtils.getOptionalIntValue(getElement(), "maxlength").ifPresent(m ->
                textfield.setDocument(new SizeLimitedDocument(m)));

        if (XHTMLUtils.isTrue(getElement(), "readonly"))
        {
            textfield.setEditable(false);
        }
        applyComponentStyle(textfield);

        return textfield;
    }

    protected void applyOriginalState()
    {
        JTextField textfield = (JTextField) getComponent();
        textfield.setText(getOriginalState().getValue());
        // Make sure we are showing the front of 'value' instead of the end.
        textfield.setCaretPosition(0);
    }

    protected String[] getFieldValues()
    {
        JTextField textfield = (JTextField) getComponent();
        return new String[] { textfield.getText() };
    }

}
