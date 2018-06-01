package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.DOMException;
import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.NamedNodeMap;
import org.xhtmlrenderer.js.dom.Node;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class NamedNodeMapImpl implements NamedNodeMap {
    @Override
    public Node getNamedItem(DOMString name) {
        return null;
    }

    @Override
    public Node setNamedItem(Node arg) throws DOMException {
        return null;
    }

    @Override
    public Node removeNamedItem(DOMString name) throws DOMException {
        return null;
    }

    @Override
    public Node item(long index) {
        return null;
    }

    @Override
    public Attribute<Long> length() {
        return null;
    }

    @Override
    public Node getNamedItemNS(DOMString namespaceURI, DOMString localName) throws DOMException {
        return null;
    }

    @Override
    public Node setNamedItemNS(Node arg) throws DOMException {
        return null;
    }

    @Override
    public Node removeNamedItemNS(DOMString namespaceURI, DOMString localName) throws DOMException {
        return null;
    }
}
