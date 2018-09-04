package org.xhtmlrenderer.script.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.script.Binder;
import org.xhtmlrenderer.script.web_idl.DOMException;
import org.xhtmlrenderer.script.whatwg_dom.Node;
import org.xhtmlrenderer.script.whatwg_dom.NodeList;
import org.xhtmlrenderer.swing.BasicPanel;

import java.util.List;

/**
 * @author Taras Maslov
 * 7/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeListImpl implements NodeList {
    
    List<org.xhtmlrenderer.dom.nodes.Node> parserNodes;
    BasicPanel panel;

    public NodeListImpl(List<org.xhtmlrenderer.dom.nodes.Node> parserNodes, BasicPanel panel) {
        this.parserNodes = parserNodes;
        this.panel = panel;
    }

    @Override
    public Node item(int index) {
        if(parserNodes.size() > index) {
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
