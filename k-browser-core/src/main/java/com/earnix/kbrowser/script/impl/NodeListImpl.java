package com.earnix.kbrowser.script.impl;

import com.earnix.kbrowser.dom.nodes.Node;
import com.earnix.kbrowser.script.Binder;
import com.earnix.kbrowser.script.web_idl.DOMException;
import com.earnix.kbrowser.script.whatwg_dom.NodeList;
import com.earnix.kbrowser.swing.BasicPanel;
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

    List<Node> parserNodes;
    BasicPanel panel;

    public NodeListImpl(List<Node> parserNodes, BasicPanel panel) {
        this.parserNodes = parserNodes;
        this.panel = panel;
    }

    @Override
    public com.earnix.kbrowser.script.whatwg_dom.Node item(int index) {
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
