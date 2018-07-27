package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.web_idl.DOMException;
import org.xhtmlrenderer.js.whatwg_dom.Node;
import org.xhtmlrenderer.js.whatwg_dom.NodeList;
import org.xhtmlrenderer.simple.XHTMLPanel;

import java.util.Iterator;
import java.util.List;

/**
 * @author Taras Maslov
 * 7/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeListImpl implements NodeList {
    
    List<org.jsoup.nodes.Node> parserNodes;
    XHTMLPanel panel;

    public NodeListImpl(List<org.jsoup.nodes.Node> parserNodes, XHTMLPanel panel) {
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
