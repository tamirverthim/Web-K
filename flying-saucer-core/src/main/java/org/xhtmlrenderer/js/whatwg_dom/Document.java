package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
public interface Document extends NonElementParentNode, DocumentOrShadowRoot, ParentNode {
    @SameObject
    @ReadonlyAttribute
    DOMImplementation implementation();

    @ReadonlyAttribute
    USVString URL();

    @ReadonlyAttribute
    USVString documentURI();

    @ReadonlyAttribute
    USVString origin();

    @ReadonlyAttribute
    DOMString compatMode();

    @ReadonlyAttribute
    DOMString characterSet();

    @ReadonlyAttribute
    DOMString charset(); // historical alias of .characterSet

    @ReadonlyAttribute
    DOMString inputEncoding(); // historical alias of .characterSet

    @ReadonlyAttribute
    DOMString contentType();

    @Nullable
    @ReadonlyAttribute
    DocumentType doctype();

    @Nullable
    @ReadonlyAttribute
    Element documentElement();

    HTMLCollection getElementsByTagName(DOMString qualifiedName);

    HTMLCollection getElementsByTagNameNS(@Nullable DOMString namespace, DOMString localName);

    HTMLCollection getElementsByClassName(DOMString classNames);

    @CEReactions
    @NewObject
    Element createElement(DOMString localName, @Optional @OneOf({DOMString.class, ElementCreationOptions.class}) Object options);

    @CEReactions
    @NewObject
    Element createElementNS(@Nullable DOMString namespace, DOMString qualifiedName, @Optional @OneOf({DOMString.class, ElementCreationOptions.class}) Object options);

    @NewObject
    DocumentFragment createDocumentFragment();

    @NewObject
    Text createTextNode(DOMString data);

    @NewObject
    CDATASection createCDATASection(DOMString data);

    @NewObject
    Comment createComment(DOMString data);

    @NewObject
    ProcessingInstruction createProcessingInstruction(DOMString target, DOMString data);

    @CEReactions
    @NewObject
    Node importNode(Node node, @Optional @DefaultBoolean(false) boolean deep);

    @CEReactions
    Node adoptNode(Node node);

    @NewObject
    Attr createAttribute(DOMString localName);

    @NewObject
    Attr createAttributeNS(@Nullable DOMString namespace, DOMString qualifiedName);

    @NewObject
    Event createEvent(DOMString inter_face);

    @NewObject
    Range createRange();

    // NodeFilter.SHOW_ALL = 0xFFFFFFFF
    @NewObject
    NodeIterator createNodeIterator(Node root, @Optional @Unsigned @DefaultLong(0xFFFFFFFF) long whatToShow, @Optional @Nullable NodeFilter filter);

    @NewObject
    TreeWalker createTreeWalker(Node root, @Optional @Unsigned @DefaultLong(0xFFFFFFFF) long whatToShow, @Optional @DefaultBoolean(false) @Nullable NodeFilter filter);
}
