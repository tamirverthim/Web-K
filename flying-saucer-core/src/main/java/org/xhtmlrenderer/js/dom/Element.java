package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface Element extends Node {

    //        readonly attribute

    @Readonly
    Attribute<DOMString> tagName();

    DOMString getAttribute(DOMString name);

    void setAttribute(DOMString name, DOMString value) throws DOMException;

    void removeAttribute(DOMString name) throws DOMException;

    Attr getAttributeNode(DOMString name);

    Attr setAttributeNode(Attr newAttr) throws DOMException;

    Attr removeAttributeNode(Attr oldAttr) throws DOMException;

    NodeList getElementsByTagName(DOMString name);

    // Introduced in DOM Level 2:
    DOMString getAttributeNS(DOMString namespaceURI, DOMString localName) throws DOMException;

    // Introduced in DOM Level 2:
    void setAttributeNS(DOMString namespaceURI, DOMString qualifiedName, DOMString value) throws DOMException;

    // Introduced in DOM Level 2:
    void removeAttributeNS(DOMString namespaceURI, DOMString localName)
            throws DOMException;

    // Introduced in DOM Level 2:
    Attr getAttributeNodeNS(DOMString namespaceURI,
                            DOMString localName) throws DOMException;

    // Introduced in DOM Level 2:
    Attr setAttributeNodeNS(Attr newAttr) throws DOMException;

    // Introduced in DOM Level 2:
    NodeList getElementsByTagNameNS(DOMString namespaceURI,
                                    DOMString localName) throws DOMException;

    // Introduced in DOM Level 2:
    boolean hasAttribute(DOMString name);

    // Introduced in DOM Level 2:
    boolean hasAttributeNS(DOMString namespaceURI,
                           DOMString localName) throws DOMException;

    // Introduced in DOM Level 3:
    //readonly attribute 

    @Readonly
    Attribute<TypeInfo> schemaTypeInfo();

    // Introduced in DOM Level 3:
    void setIdAttribute(DOMString name, boolean isId) throws DOMException;

    // Introduced in DOM Level 3:
    void setIdAttributeNS(DOMString namespaceURI, DOMString localName, boolean isId) throws DOMException;

    // Introduced in DOM Level 3:
    void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException;
};