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

import com.earnix.webk.dom.nodes.Element;
import com.earnix.webk.dom.nodes.Node;
import com.earnix.webk.dom.select.Elements;
import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;
import com.earnix.webk.simple.extend.XhtmlForm;
import com.earnix.webk.util.GeneralUtil;
import com.earnix.webk.util.XHTMLUtils;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SelectField extends FormField {

    public SelectField(Element e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        List<NameValuePair> optionList = createList();
        if (shouldRenderAsList()) {
            int size = 0;
            if (hasAttribute("size")) {
                size = GeneralUtil.parseIntRelaxed(getAttribute("size"));
            }
            JTable select = SwingComponentFactory.getInstance().createMultipleOptionsList(this, optionList, size);
            JScrollPane scrollPane = SwingComponentFactory.getInstance().createScrollPane(this);
            scrollPane.setViewportView(select);
            return scrollPane;
        } else {
            return SwingComponentFactory.getInstance().createComboBox(this, optionList);
        }
    }

    @Override
    protected FormFieldState loadOriginalState() {
        List<Integer> selectedIndices = new ArrayList();
        Elements options = getElement().getElementsByTag("option");
        for (int i = 0; i < options.size(); i++) {
            Element option = options.get(i);
            if (XHTMLUtils.isTrue(option, "selected")) {
                selectedIndices.add(new Integer(i));
            }
        }
        return FormFieldState.fromList(selectedIndices);
    }

    @Override
    protected void applyOriginalState() {
        if (shouldRenderAsList()) {
            JTable select = (JTable) ((JScrollPane) getComponent()).getViewport().getView();
            for (int i = 0; i < select.getRowCount(); i++) {
                select.setValueAt(Boolean.FALSE, i, 0);
            }
            int[] selIndices = getOriginalState().getSelectedIndices();
            for (int i = 0; i < selIndices.length; i++) {
                select.setValueAt(Boolean.TRUE, selIndices[i], 0);
            }
        } else {
            JComboBox select = (JComboBox) getComponent();
            // This looks strange, but basically since this is a single select, and
            // someone might have put selected="selected" on more than a single option
            // I believe that the correct play here is to select the _last_ option with
            // that attribute.
            int[] indices = getOriginalState().getSelectedIndices();
            if (indices.length == 0) {
                select.setSelectedIndex(0);
            } else {
                select.setSelectedIndex(indices[indices.length - 1]);
            }
        }
    }

    protected String[] getFieldValues() {
        if (shouldRenderAsList()) {
            JTable select = (JTable) ((JScrollPane) getComponent()).getViewport().getView();
            List<String> submitValues = new ArrayList<>();
            for (int i = 0; i < select.getRowCount(); i++) {
                if (Boolean.TRUE.equals(select.getValueAt(i, 0))) {
                    NameValuePair pair = (NameValuePair) select.getValueAt(i, 1);
                    if (pair.getValue() != null) {
                        submitValues.add(pair.getValue());
                    }
                }
            }
            return submitValues.toArray(new String[0]);
        } else {
            JComboBox select = (JComboBox) getComponent();
            NameValuePair selectedValue = (NameValuePair) select.getSelectedItem();
            if (selectedValue != null) {
                if (selectedValue.getValue() != null) {
                    return new String[]{selectedValue.getValue()};
                }
            }
        }
        return new String[]{};
    }

    private List<NameValuePair> createList() {
        List<NameValuePair> list = new ArrayList();
        addChildren(list, getElement());
        return list;
    }

    private void addChildren(List<NameValuePair> list, Element e) {
        List<Node> children = e.childNodes();
        for (int i = 0; i < children.size(); i++) {
            if (!(children.get(i) instanceof Element))
                continue;
            Element child = (Element) children.get(i);

            if ("option".equals(child.nodeName())) {
                // option tag, add it
                String optionText = XhtmlForm.collectText(child);
                String optionValue = optionText;

                if (child.hasAttr("value")) {
                    optionValue = child.attr("value");
                }
                list.add(new NameValuePair(optionText, optionValue));
            } else if ("optgroup".equals(child.nodeName())) {
                // optgroup tag, append heading and indent children
                String titleText = child.attr("label");
                list.add(new NameValuePair(titleText, null));
                addChildren(list, child);
            }
        }
    }

    private boolean shouldRenderAsList() {
        return XHTMLUtils.isTrue(getElement(), "multiple");
    }

    @Override
    protected Optional<String> validateInternal() {
        if (isRequired() && shouldRenderAsList() && getFieldValues().length == 0) {
            return Optional.of("At least one option must be selected");
        }
        return Optional.empty();
    }
}
