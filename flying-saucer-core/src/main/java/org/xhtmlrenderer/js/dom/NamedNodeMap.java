package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface NamedNodeMap {
    Node getNamedItem(DOMString name);

    Node setNamedItem(Node arg) throws DOMException;

    Node removeNamedItem(DOMString name) throws DOMException;

    Node item(@Unsigned long index);

    @Readonly
    @Unsigned
    Attribute<Long> length();

    // Introduced in DOM Level 2:
    Node getNamedItemNS(DOMString namespaceURI, DOMString localName) throws DOMException;

    // Introduced in DOM Level 2:
    Node setNamedItemNS(Node arg) throws DOMException;

    // Introduced in DOM Level 2:
    Node removeNamedItemNS(DOMString namespaceURI, DOMString localName) throws DOMException;
}
