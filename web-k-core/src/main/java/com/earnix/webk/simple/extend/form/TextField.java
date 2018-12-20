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

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;
import com.earnix.webk.simple.extend.XhtmlForm;
import com.earnix.webk.util.XHTMLUtils;
import lombok.val;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextField extends AbstractTextField {

    private Popup validationErrorPopup;
    private final Color BACKGROUND_INVALID = new Color(0xff9999);


    public TextField(ElementModel e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        JTextField textfield = SwingComponentFactory.getInstance().createTextField(this);
        textfield.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                getElement().attr("value", textfield.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getElement().attr("value", textfield.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                getElement().attr("value", textfield.getText());

            }
        });
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

    public Optional<Pattern> getPattern() {
        try {
            if (hasAttribute("pattern")) {
                val pattern = Pattern.compile(getAttribute("pattern"));
                return Optional.of(pattern);
            } else {
                return Optional.empty();
            }
        } catch (PatternSyntaxException ex) {
            return Optional.empty();
        }
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
    public Optional<String> validateInternal() {

        String textContent = getFieldValues()[0];
        
        if (textContent.length() == 0) {
            if (isRequired()) {
                return Optional.of("Value must be specified.");
            } else {
                return Optional.empty();
            }
        }

        if (getPattern().isPresent()) {
            val pattern = getPattern().get();
            if (!pattern.matcher(textContent).matches()) {
                return Optional.of("The specified value is invalid.");
            }
        }

        if (isNumeric()) {
            try {
                val doubleValue = Double.parseDouble(getFieldValues()[0]);

                val min = getMin();
                if (min.isPresent() && min.getAsDouble() > doubleValue) {
                    return Optional.of("The specified value must be equal to or greater than " + getAttribute("min") + ".");
                }

                val max = getMax();
                if (max.isPresent() && max.getAsDouble() < doubleValue) {
                    return Optional.of("The specified value must be equal to or less than " + getAttribute("max") + ".");
                }

            } catch (NumberFormatException e) {
                // multiple dots
                return Optional.of("The specified value must be numeric.");
            }
        }

        return Optional.empty();
    }

    public boolean isNumeric() {
        return getAttribute("type").equalsIgnoreCase("number");
    }
}
