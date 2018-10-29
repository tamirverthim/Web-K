package com.earnix.webk.script.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.script.Binder;
import com.earnix.webk.script.whatwg_dom.NonDocumentTypeChildNode;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NonDocumentTypeChildNodeImpl implements NonDocumentTypeChildNode {

    NodeModel target;
    BasicPanel panel;

    public NonDocumentTypeChildNodeImpl(NodeModel target, BasicPanel panel) {
        this.target = target;
        this.panel = panel;
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Element previousElementSibling() {
        if (target instanceof ElementModel) {
            return (com.earnix.webk.script.whatwg_dom.Element) Binder.get(((ElementModel) target).previousElementSibling(), panel);
        }

        return null;
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Element nextElementSibling() {
        if (target instanceof ElementModel) {
            return (com.earnix.webk.script.whatwg_dom.Element) Binder.get(((ElementModel) target).nextElementSibling(), panel);
        }
        return null;
    }
}
