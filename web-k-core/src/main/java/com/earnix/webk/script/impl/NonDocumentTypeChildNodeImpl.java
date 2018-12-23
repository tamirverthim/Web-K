package com.earnix.webk.script.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.NonDocumentTypeChildNode;
import com.earnix.webk.script.whatwg_dom.impl.ScriptDOMFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class NonDocumentTypeChildNodeImpl implements NonDocumentTypeChildNode {

    final ScriptContext scriptContext;
    final NodeModel target;

    @Override
    public Element previousElementSibling() {
        if (target instanceof ElementModel) {
            return (Element) ScriptDOMFactory.get(scriptContext, ((ElementModel) target).previousElementSibling());
        }

        return null;
    }

    @Override
    public Element nextElementSibling() {
        if (target instanceof ElementModel) {
            return (Element) ScriptDOMFactory.get(scriptContext, ((ElementModel) target).nextElementSibling());
        }
        return null;
    }
}
