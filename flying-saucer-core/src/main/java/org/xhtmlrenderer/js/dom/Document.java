package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface Document {
    // Modified in DOM Level 3:
    @Readonly
    Attribute<DocumentType> doctype();

    @Readonly
    Attribute<DOMImplementation> implementation();

    @Readonly
    Attribute<Element> documentElement();

    Element createElement(DOMString tagName) throws DOMException;

    DocumentFragment createDocumentFragment();

    Text createTextNode(DOMString data);

    Comment createComment(DOMString data);

    CDATASection createCDATASection(DOMString data) throws DOMException;

    ProcessingInstruction createProcessingInstruction(DOMString target,
                                                      DOMString data) throws DOMException;

    Attr createAttribute(DOMString name) throws DOMException;

    EntityReference createEntityReference(DOMString name) throws DOMException;

    NodeList getElementsByTagName(DOMString tagname);

    // Introduced in DOM Level 2:
    Node importNode(Node importedNode,
                    boolean deep) throws DOMException;

    // Introduced in DOM Level 2:
    Element createElementNS(DOMString namespaceURI,
                            DOMString qualifiedName) throws DOMException;

    // Introduced in DOM Level 2:
    Attr createAttributeNS(DOMString namespaceURI,
                           DOMString qualifiedName) throws DOMException;

    // Introduced in DOM Level 2:
    NodeList getElementsByTagNameNS(DOMString namespaceURI,
                                    DOMString localName);

    // Introduced in DOM Level 2:
    Element getElementById(DOMString elementId);

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<DOMString> inputEncoding();

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<DOMString> xmlEncoding();

    // Introduced in DOM Level 3:
    Attribute<Boolean> xmlStandalone();
    // raises(DOMException) on setting

    // Introduced in DOM Level 3:
    Attribute<DOMString> xmlVersion();
    // raises(DOMException) on setting

    // Introduced in DOM Level 3:
    Attribute<Boolean> strictErrorChecking();

    // Introduced in DOM Level 3:
    Attribute<DOMString> documentURI();

    // Introduced in DOM Level 3:
    Node adoptNode(Node source) throws DOMException;

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<DOMConfiguration> domConfig();

    // Introduced in DOM Level 3:
    void normalizeDocument();

    // Introduced in DOM Level 3:
    Node renameNode(Node n, DOMString namespaceURI, DOMString qualifiedName) throws DOMException;
}
