package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.DOMLocator;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.dom.Node;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class DOMLocatorImpl implements DOMLocator {
    @Override
    public Attribute<Long> lineNumber() {
        return null;
    }

    @Override
    public Attribute<Long> columnNumber() {
        return null;
    }

    @Override
    public Attribute<Long> byteOffset() {
        return null;
    }

    @Override
    public Attribute<Long> utf16Offset() {
        return null;
    }

    @Override
    public Attribute<Node> relatedNode() {
        return null;
    }

    @Override
    public Attribute<DOMString> uri() {
        return null;
    }
}
