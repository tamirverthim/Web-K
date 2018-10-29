package com.earnix.webk.script.impl;

import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.script.Binder;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.whatwg_dom.NodeList;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.List;

/**
 * @author Taras Maslov
 * 7/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeListImpl implements NodeList {

    List<NodeModel> parserNodes;
    BasicPanel panel;

    public NodeListImpl(List<NodeModel> parserNodes, BasicPanel panel) {
        this.parserNodes = parserNodes;
        this.panel = panel;
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Node item(int index) {
        if (parserNodes.size() > index) {
            val parsed = parserNodes.get((int) index);
            return Binder.get(parsed, panel);
        } else {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public int length() {
        return parserNodes.size();
    }
}
