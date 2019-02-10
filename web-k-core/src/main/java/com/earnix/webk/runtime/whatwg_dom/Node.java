package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.CEReactions;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.DefaultBoolean;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.NewObject;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.SameObject;
import com.earnix.webk.runtime.web_idl.USVString;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface Node extends EventTarget {

    @Unsigned short ELEMENT_NODE = 1;
    @Unsigned short ATTRIBUTE_NODE = 2;
    @Unsigned short TEXT_NODE = 3;
    @Unsigned short CDATA_SECTION_NODE = 4;
    @Unsigned short ENTITY_REFERENCE_NODE = 5; // historical
    @Unsigned short ENTITY_NODE = 6; // historical
    @Unsigned short PROCESSING_INSTRUCTION_NODE = 7;
    @Unsigned short COMMENT_NODE = 8;
    @Unsigned short DOCUMENT_NODE = 9;
    @Unsigned short DOCUMENT_TYPE_NODE = 10;
    @Unsigned short DOCUMENT_FRAGMENT_NODE = 11;
    @Unsigned short NOTATION_NODE = 12; // historical

    @ReadonlyAttribute
    @Unsigned
    short nodeType();

    @ReadonlyAttribute
    @DOMString String nodeName();

    @ReadonlyAttribute
    @USVString
    String baseURI();

    @ReadonlyAttribute
    boolean isConnected();

    @Optional
    @ReadonlyAttribute
    Document ownerDocument();

    Node getRootNode(@Optional GetRootNodeOptions options);

    @Nullable
    @ReadonlyAttribute
    Node parentNode();

    @Nullable
    @ReadonlyAttribute
    Element parentElement();

    boolean hasChildNodes();

    @SameObject
    @ReadonlyAttribute
    NodeList childNodes();

    @Nullable
    @ReadonlyAttribute
    Node firstChild();

    @Nullable
    @ReadonlyAttribute
    Node lastChild();

    @Nullable
    @ReadonlyAttribute
    Node previousSibling();

    @Nullable
    @ReadonlyAttribute
    Node nextSibling();

    @Nullable
    @CEReactions
    @DOMString
    Attribute<String> nodeValue();

    @Nullable
    @CEReactions
    @DOMString
    Attribute<String> textContent();

    @CEReactions
    void normalize();

    @CEReactions
    @NewObject
    Node cloneNode(@Optional @DefaultBoolean(false) boolean deep);

    boolean isEqualNode(@Optional Node otherNode);

    boolean isSameNode(@Optional Node otherNode); // historical alias of ===

    @Unsigned short DOCUMENT_POSITION_DISCONNECTED = 0x01;
    @Unsigned short DOCUMENT_POSITION_PRECEDING = 0x02;
    @Unsigned short DOCUMENT_POSITION_FOLLOWING = 0x04;
    @Unsigned short DOCUMENT_POSITION_CONTAINS = 0x08;
    @Unsigned short DOCUMENT_POSITION_CONTAINED_BY = 0x10;
    @Unsigned short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 0x20;

    @Unsigned
    short compareDocumentPosition(Node other);

    boolean contains(@Optional Node other);

    @Optional
    @DOMString String lookupPrefix(@Optional @DOMString String namespace);

    @Optional
    @DOMString String lookupNamespaceURI(@Optional @DOMString String prefix);

    boolean isDefaultNamespace(@Optional @DOMString String namespace);

    @CEReactions
    Node insertBefore(Node node, @Optional Node child);

    @CEReactions
    Node appendChild(Node node);

    @CEReactions
    Node replaceChild(Node node, Node child);

    @CEReactions
    Node removeChild(Node child);
}
