package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.CEReactions;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.DefaultBoolean;
import com.earnix.webk.runtime.web_idl.DefaultLong;
import com.earnix.webk.runtime.web_idl.NewObject;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.OneOf;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.SameObject;
import com.earnix.webk.runtime.web_idl.USVString;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
public interface Document extends NonElementParentNode, DocumentOrShadowRoot, ParentNode {

    @SameObject
    @ReadonlyAttribute
    DOMImplementation implementation();

    @ReadonlyAttribute
    @USVString
    String URL();

    @ReadonlyAttribute
    @USVString
    String documentURI();

    @ReadonlyAttribute
    @USVString
    String origin();

    @ReadonlyAttribute
    @DOMString
    String compatMode();

    @ReadonlyAttribute
    @DOMString
    String characterSet();

    @ReadonlyAttribute
    @DOMString
    String charset(); // historical alias of .characterSet

    @ReadonlyAttribute
    @DOMString
    String inputEncoding(); // historical alias of .characterSet

    @ReadonlyAttribute
    @DOMString
    String contentType();

    @Nullable
    @ReadonlyAttribute
    DocumentType doctype();

    @Nullable
    @ReadonlyAttribute
    Element documentElement();

    HTMLCollection getElementsByTagName(@DOMString String qualifiedName);

    HTMLCollection getElementsByTagNameNS(@Nullable @DOMString String namespace, @DOMString String localName);

    HTMLCollection getElementsByClassName(@DOMString String classNames);

    @CEReactions
    @NewObject
    Element createElement(@DOMString String localName, @Optional @DOMString(oneOfIndex = 0) @OneOf({String.class, ElementCreationOptions.class}) Object options);

    @CEReactions
    @NewObject
    Element createElementNS(@Nullable @DOMString String namespace, @DOMString String qualifiedName, @Optional @DOMString @OneOf({String.class, ElementCreationOptions.class}) Object options);

    @NewObject
    DocumentFragment createDocumentFragment();

    @NewObject
    Text createTextNode(@DOMString String data);

    @NewObject
    CDATASection createCDATASection(@DOMString String data);

    @NewObject
    Comment createComment(@DOMString String data);

    @NewObject
    ProcessingInstruction createProcessingInstruction(@DOMString String target, @DOMString String data);

    @CEReactions
    @NewObject
    Node importNode(Node node, @Optional @DefaultBoolean(false) boolean deep);

    @CEReactions
    Node adoptNode(Node node);

    @NewObject
    Attr createAttribute(String localName);

    @NewObject
    Attr createAttributeNS(@Nullable String namespace, String qualifiedName);

    @NewObject
    Event createEvent(String inter_face);

    @NewObject
    Range createRange();

    // NodeFilter.SHOW_ALL = 0xFFFFFFFF
    @NewObject
    NodeIterator createNodeIterator(Node root, @Optional @Unsigned @DefaultLong(0xFFFFFFFF) long whatToShow, @Optional @Nullable NodeFilter filter);

    @NewObject
    TreeWalker createTreeWalker(Node root, @Optional @Unsigned @DefaultLong(0xFFFFFFFF) long whatToShow, @Optional @DefaultBoolean(false) @Nullable NodeFilter filter);

}
