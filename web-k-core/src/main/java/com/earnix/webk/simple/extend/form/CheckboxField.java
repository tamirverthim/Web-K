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
import lombok.val;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckboxField extends InputField {

    private ActionListener listener;

    public CheckboxField(ElementModel e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        JCheckBox checkbox = SwingComponentFactory.getInstance().createCheckBox(this);

        Dimension ps = checkbox.getPreferredSize();
        intrinsicWidth = new Integer(ps.width + 1);
        intrinsicHeight = new Integer(ps.height);

        checkbox.addActionListener(listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkbox.isSelected()) {
                    CheckboxField.this.getElement().attr("checked", "checked");
                } else {
                    CheckboxField.this.getElement().removeAttr("checked");
                }
                val panel = CheckboxField.this.getContext().getSharedContext().getCanvas();
                panel.getScriptContext().getEventManager().onchange(CheckboxField.this.getElement());
            }
        });
        return checkbox;
    }

    protected FormFieldState loadOriginalState() {
        return FormFieldState.fromBoolean(
                getAttribute("checked").equalsIgnoreCase("checked"));
    }

    protected void applyOriginalState() {

        JToggleButton button = (JToggleButton) getComponent();

        button.removeActionListener(listener);
        
        button.setSelected(getOriginalState().isChecked());

        button.addActionListener(listener);
    }

    protected String[] getFieldValues() {
        JToggleButton button = (JToggleButton) getComponent();

        if (button.isSelected()) {
            return new String[]{
                    hasAttribute("value") ? getAttribute("value") : "on"
            };
        } else {
            return new String[]{};
        }
    }
}
