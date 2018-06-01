package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.*;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class DOMImplementationImpl implements DOMImplementation {
    @Override
    public boolean hasFeature(DOMString feature, DOMString version) {
        return false;
    }

    @Override
    public DocumentType createDocumentType(DOMString qualifiedName, DOMString publicId, DOMString systemId) throws DOMException {
        return null;
    }

    @Override
    public Document createDocument(DOMString namespaceURI, DOMString qualifiedName, DocumentType doctype) throws DOMException {
        return null;
    }

    @Override
    public DOMObject getFeature(DOMString feature, DOMString version) {
        return null;
    }
}
