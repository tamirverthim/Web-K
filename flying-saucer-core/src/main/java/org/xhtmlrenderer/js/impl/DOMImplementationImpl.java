package org.xhtmlrenderer.js.impl;

import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.whatwg_dom.DOMImplementation;
import org.xhtmlrenderer.js.whatwg_dom.Document;
import org.xhtmlrenderer.js.whatwg_dom.DocumentType;
import org.xhtmlrenderer.js.whatwg_dom.XMLDocument;

/**
 * @author Taras Maslov
 * 7/3/2018
 */
public class DOMImplementationImpl implements DOMImplementation {
    @Override
    public DocumentType createDocumentType(DOMString qualifiedName, DOMString publicId, DOMString systemId) {
        return null;
    }

    @Override
    public XMLDocument createDocument(DOMString namespace, DOMString qualifiedName, DocumentType doctype) {
        return null;
    }

    @Override
    public Document createHTMLDocument(DOMString title) {
        return null;
    }

    @Override
    public boolean hasFeature() {
        return false;
    }
}
