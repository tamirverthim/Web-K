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


import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.XhtmlForm;

public class FormFieldFactory {
    private FormFieldFactory() {
    }

    public static FormField create(XhtmlForm form, LayoutContext context, BlockBox box) {
        String typeKey = null;

        org.xhtmlrenderer.dom.nodes.Element e = box.getElement();

        if (e.nodeName().equalsIgnoreCase("input")) {
            typeKey = e.attr("type");  
        } else if (e.nodeName().equalsIgnoreCase("textarea")) {
            typeKey = "textarea";
        } else if (e.nodeName().equalsIgnoreCase("select")) {
            typeKey = "select";
        } else {
            return null;
        }

        if (typeKey.equalsIgnoreCase("submit")) {
            return new SubmitField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("reset")) {
            return new ResetField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("button")) {
            return new ButtonField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("image")) {
            return new ImageField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("hidden")) {
            return new HiddenField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("password")) {
            return new PasswordField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("checkbox")) {
            return new CheckboxField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("radio")) {
            return new RadioButtonField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("file")) {
            return new FileField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("textarea")) {
            return new TextAreaField(e, form, context, box);
        } else if (typeKey.equalsIgnoreCase("select")) {
            return new SelectField(e, form, context, box);
        } else {
            return new TextField(e, form, context, box);
        }
    }
}
