package org.xhtmlrenderer.js.impl;

import lombok.val;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.web_idl.DOMException;
import org.xhtmlrenderer.js.whatwg_dom.Node;
import org.xhtmlrenderer.js.whatwg_dom.NodeList;

import java.util.Iterator;
import java.util.List;

/**
 * @author Taras Maslov
 * 7/12/2018
 */
public class NodeListImpl implements NodeList {
    
    List<org.jsoup.nodes.Node> parserNodes;

    public NodeListImpl(List<org.jsoup.nodes.Node> parserNodes) {
        this.parserNodes = parserNodes;
    }

    @Override
    public Node item(long index) {
        if(parserNodes.size() > index) {
            val parsed = parserNodes.get((int) index);
            return Binder.get(parsed);
        } else {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public long length() {
        return parserNodes.size();
    }

    @Override
    public Iterator<Node> iterator() {
        return null;
    }
}
