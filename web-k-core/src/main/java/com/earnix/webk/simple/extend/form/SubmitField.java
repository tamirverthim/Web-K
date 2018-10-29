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
import com.earnix.webk.util.XRLog;

import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubmitField extends AbstractButtonField {
    public SubmitField(ElementModel e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        JButton button = SwingComponentFactory.getInstance().createButton(this);

        String value;
        if (hasAttribute("value")) {
            value = getAttribute("value");
            if (value.length() == 0) {
                value = " ";    //otherwise we get a very short button
            }
        } else {
            value = "Submit";
        }

        button.setText(value);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                XRLog.layout("Submit pressed: Submit");

                getParentForm().submit(getComponent());
            }
        });

        return button;
    }

    public boolean includeInSubmission(JComponent source) {
        return (source == getComponent());
    }

    protected String[] getFieldValues() {
        return new String[]{
                hasAttribute("value") ? getAttribute("value") : "Submit" // TODO: Don't hardcode 
        };
    }
}
