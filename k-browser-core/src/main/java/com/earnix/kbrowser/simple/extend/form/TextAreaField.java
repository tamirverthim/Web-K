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
package com.earnix.kbrowser.simple.extend.form;

import com.earnix.kbrowser.dom.nodes.Element;
import com.earnix.kbrowser.layout.LayoutContext;
import com.earnix.kbrowser.render.BlockBox;
import com.earnix.kbrowser.simple.extend.XhtmlForm;
import com.earnix.kbrowser.util.XHTMLUtils;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaField extends AbstractTextField {

    public TextAreaField(Element e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        int rows = XHTMLUtils.getIntValue(getElement(), "rows", 4);
        int cols = XHTMLUtils.getIntValue(getElement(), "cols", 10);

        JTextArea textArea = SwingComponentFactory.getInstance().createTextArea(this, rows, cols);

        JScrollPane scrollPane = SwingComponentFactory.getInstance().createScrollPane(this);
        scrollPane.setViewportView(textArea);
        applyComponentStyle(scrollPane);

        return scrollPane;
    }

    protected FormFieldState loadOriginalState() {
        return FormFieldState.fromString(XhtmlForm.collectText(getElement()));
    }

    protected void applyOriginalState() {
        textArea().setText(getOriginalState().getValue());
    }

    protected String[] getFieldValues() {
        return new String[]{textArea().getText()};
    }

    private JTextArea textArea() {
        return (JTextArea) ((JScrollPane) getComponent()).getViewport().getView();
    }
}
