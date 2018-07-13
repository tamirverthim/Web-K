package org.xhtmlrenderer.js.impl;

import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.whatwg_dom.DocumentType;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
public class DocumentTypeImpl extends NodeImpl implements DocumentType {
    
    private org.jsoup.nodes.DocumentType target;

    public DocumentTypeImpl(org.jsoup.nodes.DocumentType target) {
        this.target = target;
    }

    @Override
    public DOMString name() {
        return DOMStringImpl.of(target.attr("name"));
    }

    @Override
    public DOMString publicId() {
        return DOMStringImpl.of(target.attr("publicId"));
    }

    @Override
    public DOMString systemId() {
        return DOMStringImpl.of(target.attr("systemId"));
    }
}
