package com.earnix.kbrowser.script.impl;

import com.earnix.kbrowser.dom.nodes.Element;
import com.earnix.kbrowser.dom.nodes.Node;
import com.earnix.kbrowser.script.Binder;
import com.earnix.kbrowser.script.whatwg_dom.NonDocumentTypeChildNode;
import com.earnix.kbrowser.swing.BasicPanel;
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
    public com.earnix.kbrowser.script.whatwg_dom.Element previousElementSibling() {
        if (target instanceof Element) {
            return (com.earnix.kbrowser.script.whatwg_dom.Element) Binder.get(((Element) target).previousElementSibling(), panel);
        }

        return null;
    }

    @Override
    public com.earnix.kbrowser.script.whatwg_dom.Element nextElementSibling() {
        if (target instanceof Element) {
            return (com.earnix.kbrowser.script.whatwg_dom.Element) Binder.get(((Element) target).nextElementSibling(), panel);
        }
        return null;
    }
}
