package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface Node {
    // NodeType
    public static final short ELEMENT_NODE = 1;
    public static final short ATTRIBUTE_NODE = 2;
    public static final short TEXT_NODE = 3;
    public static final short CDATA_SECTION_NODE = 4;
    public static final short ENTITY_REFERENCE_NODE = 5;
    public static final short ENTITY_NODE = 6;
    public static final short PROCESSING_INSTRUCTION_NODE = 7;
    public static final short COMMENT_NODE = 8;
    public static final short DOCUMENT_NODE = 9;
    public static final short DOCUMENT_TYPE_NODE = 10;
    public static final short DOCUMENT_FRAGMENT_NODE = 11;
    public static final short NOTATION_NODE = 12;

    @Readonly
    Attribute<DOMString> nodeName();

    // raises(DOMException) on setting
    // raises(DOMException) on retrieval
    Attribute<DOMString> nodeValue() throws DOMException;


    @Readonly
    @Unsigned
    Attribute<Short> nodeType();

    @Readonly
    Attribute<Node> parentNode();

    @Readonly
    Attribute<NodeList> childNodes();

    @Readonly
    Attribute<Node> firstChild();

    @Readonly
    Attribute<Node> lastChild();

    @Readonly
    Attribute<Node> previousSibling();

    @Readonly
    Attribute<Node> nextSibling();

    @Readonly
    Attribute<NamedNodeMap> attributes();

    // Modified in DOM Level 2:
    @Readonly
    Attribute<Document> ownerDocument();

    // Modified in DOM Level 3:
    Node insertBefore(Node newChild, Node refChild) throws DOMException;

    // Modified in DOM Level 3:
    Node replaceChild(Node newChild, Node oldChild) throws DOMException;

    // Modified in DOM Level 3:
    Node removeChild(Node oldChild) throws DOMException;

    // Modified in DOM Level 3:
    Node appendChild(Node newChild) throws DOMException;

    boolean hasChildNodes();

    Node cloneNode(boolean deep);

    // Modified in DOM Level 3:
    void normalize();

    // Introduced in DOM Level 2:
    boolean isSupported(DOMString feature,
                        DOMString version);

    // Introduced in DOM Level 2:
    @Readonly
    Attribute<DOMString> namespaceURI();

    // Introduced in DOM Level 2:
    Attribute<DOMString> prefix();
    // raises(DOMException) on setting

    // Introduced in DOM Level 2:
    @Readonly
    Attribute<DOMString> localName();

    // Introduced in DOM Level 2:
    boolean hasAttributes();

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<DOMString> baseURI();

    // DocumentPosition
    public static final short DOCUMENT_POSITION_DISCONNECTED = 0x01;
    public static final short DOCUMENT_POSITION_PRECEDING = 0x02;
    public static final short DOCUMENT_POSITION_FOLLOWING = 0x04;
    public static final short DOCUMENT_POSITION_CONTAINS = 0x08;
    public static final short DOCUMENT_POSITION_CONTAINED_BY = 0x10;
    public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 0x20;

    // Introduced in DOM Level 3:
    @Unsigned
    short compareDocumentPosition(Node other) throws DOMException;

    // Introduced in DOM Level 3:
    Attribute<DOMString> textContent();
    // raises(DOMException) on setting
    // raises(DOMException) on retrieval

    // Introduced in DOM Level 3:
    boolean isSameNode(Node other);

    // Introduced in DOM Level 3:
    DOMString lookupPrefix(DOMString namespaceURI);

    // Introduced in DOM Level 3:
    boolean isDefaultNamespace(DOMString namespaceURI);

    // Introduced in DOM Level 3:
    DOMString lookupNamespaceURI(DOMString prefix);

    // Introduced in DOM Level 3:
    boolean isEqualNode(Node arg);

    // Introduced in DOM Level 3:
    DOMObject getFeature(DOMString feature, DOMString version);

    // Introduced in DOM Level 3:
    DOMUserData setUserData(DOMString key,
                            DOMUserData data,
                            UserDataHandler handler);

    // Introduced in DOM Level 3:
    DOMUserData getUserData(DOMString key);
}
