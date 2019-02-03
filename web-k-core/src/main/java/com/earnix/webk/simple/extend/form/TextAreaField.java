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
package com.earnix.webk.simple.extend.form;

import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.simple.extend.XhtmlForm;
import com.earnix.webk.util.XHTMLUtils;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

public class TextAreaField extends AbstractTextField {

    private DocumentListener listener;
    private FormFieldState preFocusState;

    public TextAreaField(ElementImpl e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        preFocusState = getOriginalState();
        int rows = XHTMLUtils.getIntValue(getElement(), "rows", 4);
        int cols = XHTMLUtils.getIntValue(getElement(), "cols", 10);

        JTextArea textArea = SwingComponentFactory.getInstance().createTextArea(this, rows, cols);
        listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                valueChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                valueChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                valueChanged();
            }
        };
        textArea.getDocument().addDocumentListener(listener);
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                preFocusState = FormFieldState.fromString(getFieldValues()[0]);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!Objects.equals(preFocusState.getValue(), getFieldValues()[0])) {
                    val scriptContext = getContext().getSharedContext().getCanvas().getScriptContext();
                    scriptContext.getEventManager().onchange(getElement());
                }
            }
        });
        
        JScrollPane scrollPane = SwingComponentFactory.getInstance().createScrollPane(this);
        scrollPane.setViewportView(textArea);
        applyComponentStyle(scrollPane);

        return scrollPane;
    }

    protected FormFieldState loadOriginalState() {
        val value = getElement().attr("value");
        if (StringUtils.isNotBlank(value)) {
            return FormFieldState.fromString(value);
        } else {
            return FormFieldState.fromString(XhtmlForm.collectText(getElement()));
        }
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
