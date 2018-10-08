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

import com.earnix.kbrowser.layout.LayoutContext;
import com.earnix.kbrowser.render.BlockBox;
import com.earnix.kbrowser.simple.extend.XhtmlForm;
import lombok.val;

public class DefaultFormFieldFactory implements FormFieldFactory {

    @Override
    public FormField create(XhtmlForm form, LayoutContext context, BlockBox box) {

        final String typeKey;

        val element = box.getElement();
        val nodeName = element.nodeName().toLowerCase();

        switch (nodeName) {
            case "input":
                typeKey = element.attr("type").toLowerCase();
                break;
            case "textarea":
                typeKey = "textarea";
                break;
            case "select":
                typeKey = "select";
                break;
            default:
                return null;
        }

        switch (typeKey) {
            case "submit":
                return new SubmitField(element, form, context, box);
            case "reset":
                return new ResetField(element, form, context, box);
            case "button":
                return new ButtonField(element, form, context, box);
            case "image":
                return new ImageField(element, form, context, box);
            case "hidden":
                return new HiddenField(element, form, context, box);
            case "password":
                return new PasswordField(element, form, context, box);
            case "checkbox":
                return new CheckboxField(element, form, context, box);
            case "radio":
                return new RadioButtonField(element, form, context, box);
            case "file":
                return new FileField(element, form, context, box);
            case "textarea":
                return new TextAreaField(element, form, context, box);
            case "select":
                return new SelectField(element, form, context, box);
            default: // handling "number" there too
                return new TextField(element, form, context, box);
        }

    }
}
