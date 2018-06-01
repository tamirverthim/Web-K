package org.xhtmlrenderer.js.dom;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface DOMImplementation {
    
    boolean hasFeature(DOMString feature,
                       DOMString version);

    // Introduced in DOM Level 2:
    DocumentType createDocumentType(DOMString qualifiedName,
                                    DOMString publicId,
                                    DOMString systemId) throws DOMException;

    // Introduced in DOM Level 2:
    Document createDocument(DOMString namespaceURI,
                            DOMString qualifiedName,
                            DocumentType doctype) throws DOMException;

    // Introduced in DOM Level 3:
    DOMObject getFeature(DOMString feature, DOMString version);
}
