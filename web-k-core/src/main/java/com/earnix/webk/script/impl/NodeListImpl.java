package com.earnix.webk.script.impl;

import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.whatwg_dom.NodeList;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author Taras Maslov
 * 7/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeListImpl implements NodeList {

    List<NodeImpl> model;
    ScriptContext ctx;

    public NodeListImpl(List<NodeImpl> model) {
        this.model = model;
        this.ctx = ctx;
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Node item(int index) {
        if (model.size() > index) {
            return model.get(index);
        } else {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public int length() {
        return model.size();
    }
}
