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

import javax.swing.JComponent;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RadioButtonField extends InputField {
    private ItemListener listener;

    public RadioButtonField(ElementModel e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        originalState = FormFieldState.fromBoolean(getAttribute("checked").equalsIgnoreCase("checked"));

        JToggleButton radio = SwingComponentFactory.getInstance().createRadioButton(this);

        String groupName = null;

        if (hasAttribute("name")) {
            groupName = getAttribute("name");
        }

        // Add to the group for mutual exclusivity
        getParentForm().addButtonToGroup(groupName, radio);

        radio.addItemListener(listener = e -> {

            val wasChecked = getAttribute("checked").equalsIgnoreCase("checked");
            val checked = e.getStateChange() == ItemEvent.SELECTED;

            if (checked) {
                getElement().attr("checked", "checked");
            } else {
                getElement().removeAttr("checked");
            }

            if (checked && !wasChecked) {
                val panel = getContext().getSharedContext().getCanvas();
                panel.getScriptContext().getEventManager().onchange(getElement());
            }
        });
        
        return radio;
    }

    protected FormFieldState loadOriginalState() {
        return originalState;
    }

    protected void applyOriginalState() {

        JToggleButton button = (JToggleButton) getComponent();
        button.removeItemListener(listener);
        button.setSelected(getOriginalState().isChecked());
        button.addItemListener(listener);
    }

    protected String[] getFieldValues() {
        JToggleButton button = (JToggleButton) getComponent();

        if (button.isSelected()) {
            return new String[]{
                    hasAttribute("value") ? getAttribute("value") : ""
            };
        } else {
            return new String[]{};
        }
    }
}
