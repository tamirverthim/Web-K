package com.earnix.webk.script.impl;

import com.earnix.webk.dom.nodes.Element;
import com.earnix.webk.dom.nodes.Node;
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

    Node target;
    BasicPanel panel;

    public NonDocumentTypeChildNodeImpl(Node target, BasicPanel panel) {
        this.target = target;
        this.panel = panel;
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Element previousElementSibling() {
        if (target instanceof Element) {
            return (com.earnix.webk.script.whatwg_dom.Element) Binder.get(((Element) target).previousElementSibling(), panel);
        }

        return null;
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Element nextElementSibling() {
        if (target instanceof Element) {
            return (com.earnix.webk.script.whatwg_dom.Element) Binder.get(((Element) target).nextElementSibling(), panel);
        }
        return null;
    }
}
