package com.earnix.webk.script.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.script.whatwg_dom.impl.ScriptDOMFactory;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.NonDocumentTypeChildNode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NonDocumentTypeChildNodeImpl implements NonDocumentTypeChildNode {

    NodeModel target;
    ScriptContext ctx;

    public NonDocumentTypeChildNodeImpl(NodeModel target, ScriptContext ctx) {
        this.target = target;
        this.ctx = ctx;
    }

    @Override
    public Element previousElementSibling() {
        if (target instanceof ElementModel) {
            return (Element) ScriptDOMFactory.get(((ElementModel) target).previousElementSibling(), ctx);
        }

        return null;
    }

    @Override
    public Element nextElementSibling() {
        if (target instanceof ElementModel) {
            return (Element) ScriptDOMFactory.get(((ElementModel) target).nextElementSibling(), ctx);
        }
        return null;
    }
}
