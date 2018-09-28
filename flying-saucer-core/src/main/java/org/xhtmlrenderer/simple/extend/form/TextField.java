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

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.dom.nodes.Element;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.XhtmlForm;
import org.xhtmlrenderer.util.XHTMLUtils;

import java.awt.*;
import java.util.Optional;
import java.util.OptionalDouble;

public class TextField extends AbstractTextField {

    private Popup validationErrorPopup;
    private final Color BACKGROUND_INVALID = new Color(0xff9999);


    public TextField(Element e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        JTextField textfield = SwingComponentFactory.getInstance().createTextField(this);
        textfield.setColumns(XHTMLUtils.getIntValue(getElement(), "size", 15));

        XHTMLUtils.getOptionalIntValue(getElement(), "maxlength").ifPresent(m ->
                textfield.setDocument(new SizeLimitedDocument(m)));
        
        if (XHTMLUtils.isTrue(getElement(), "readonly")) {
            textfield.setEditable(false);
        }
        applyComponentStyle(textfield);
        
        return textfield;
    }

    protected void applyOriginalState() {
        JTextField textfield = (JTextField) getComponent();
        textfield.setText(getOriginalState().getValue());
        // Make sure we are showing the front of 'value' instead of the end.
        textfield.setCaretPosition(0);
    }
    
    protected String[] getFieldValues() {
        JTextField textfield = (JTextField) getComponent();
        return new String[]{textfield.getText()};
    }

    public OptionalDouble getMin() {
        return getDoubleAttribute("min");
    }

    public OptionalDouble getMax() {
        return getDoubleAttribute("max");
    }

    protected OptionalDouble getDoubleAttribute(String attribute) {
        if (hasAttribute(attribute)) {
            try {
                double min = Double.parseDouble(getAttribute(attribute));
                return OptionalDouble.of(min);
            } catch (NumberFormatException e) {
                return OptionalDouble.empty();
            }
        }
        return OptionalDouble.empty();
    }

    @Override
    public Optional<String> validateInternal () {
        
        if(getFieldValues()[0].length() == 0) {
            if (isRequired()) {
                return Optional.of("Field value is required");
            } else {
                return Optional.empty();
            }
        }
        
        if (isNumeric()) {
            try {
                val doubleValue = Double.parseDouble(getFieldValues()[0]);
                
                val min = getMin();
                if (min.isPresent() && min.getAsDouble() > doubleValue) {
                    return Optional.of("Value must be greater or equal " + getAttribute("min") + ".");
                }

                val max = getMax();
                if (max.isPresent() && max.getAsDouble() > doubleValue) {
                    return Optional.of("Value must be lower or equal " + getAttribute("max") + ".");
                }

            } catch (NumberFormatException e) {
                // multiple dots
                return Optional.of("Value must be numeric.");
            }
        }

        return Optional.empty();
    }

    public boolean isNumeric() {
        return getAttribute("type").equalsIgnoreCase("number");
    }
}
