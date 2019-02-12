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
import com.earnix.webk.runtime.dom.impl.ElementImpl;
import com.earnix.webk.simple.extend.XhtmlForm;

import javax.swing.JButton;
import javax.swing.JComponent;

public class ButtonField extends AbstractButtonField {

    public ButtonField(ElementImpl e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        JButton button = SwingComponentFactory.getInstance().createButton(this);

        String value;//
        if (getElement().nodeName().equals("button")) {
            value = getElement().text();
        } else {
            // <input type=button>
            value = getAttribute("value");
        }

        applyComponentStyle(button);

        button.setText(value);

        return button;
    }

    public boolean includeInSubmission(JComponent source) {
        return false;
    }
}
