package org.xhtmlrenderer.js.dom.impl;

import lombok.val;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.dom.*;
import org.xhtmlrenderer.js.canvas.impl.HTMLCanvasElementImpl;
import org.xhtmlrenderer.js.web_idl.Attribute;

import java.util.Objects;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class ElementImpl extends NodeImpl implements Element {

    private org.jsoup.nodes.Element target;

    public ElementImpl(org.jsoup.nodes.Element target) {
        super(target);
        this.target = target;
    }

    public static ElementImpl create(org.jsoup.nodes.Element target) {
        val bound = Binder.get(target);

        if (bound != null) {
            // element created by renderer
            return (ElementImpl) bound;
        } else if (Objects.equals(target.tagName(), "canvas")) {
            return new HTMLCanvasElementImpl(target);
        }
        return new ElementImpl(target);
    }


    @Override
    public Attribute<DOMString> tagName() {
        return Attribute.<DOMString>readOnly().give(() -> new DOMStringImpl(target.tagName()));
    }

    @Override
    public DOMString getAttribute(DOMString name) {
        return new DOMStringImpl(target.attr(name.toString()));
    }

    @Override
    public void setAttribute(DOMString name, DOMString value) throws DOMException {
        target.attr(name.toString(), value.toString());
    }

    @Override
    public void removeAttribute(DOMString name) throws DOMException {
        target.attr(name.toString());
    }

    @Override
    public Attr getAttributeNode(DOMString name) {
        return null;
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        return null;
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagName(DOMString name) {
        return null;
    }

    @Override
    public DOMString getAttributeNS(DOMString namespaceURI, DOMString localName) throws DOMException {
        return null;
    }

    @Override
    public void setAttributeNS(DOMString namespaceURI, DOMString qualifiedName, DOMString value) throws DOMException {

    }

    @Override
    public void removeAttributeNS(DOMString namespaceURI, DOMString localName) throws DOMException {

    }

    @Override
    public Attr getAttributeNodeNS(DOMString namespaceURI, DOMString localName) throws DOMException {
        return null;
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagNameNS(DOMString namespaceURI, DOMString localName) throws DOMException {
        return null;
    }

    @Override
    public boolean hasAttribute(DOMString name) {
        return false;
    }

    @Override
    public boolean hasAttributeNS(DOMString namespaceURI, DOMString localName) throws DOMException {
        return false;
    }

    @Override
    public Attribute<TypeInfo> schemaTypeInfo() {
        return null;
    }

    @Override
    public void setIdAttribute(DOMString name, boolean isId) throws DOMException {

    }

    @Override
    public void setIdAttributeNS(DOMString namespaceURI, DOMString localName, boolean isId) throws DOMException {

    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {

    }

    @Override
    public Attribute<DOMString> nodeName() {
        return null;
    }

    @Override
    public Attribute<DOMString> nodeValue() throws DOMException {
        return null;
    }

    @Override
    public Attribute<Short> nodeType() {
        return null;
    }

    @Override
    public Attribute<Node> parentNode() {
        return null;
    }

    @Override
    public Attribute<NodeList> childNodes() {
        return null;
    }

    @Override
    public Attribute<Node> firstChild() {
        return null;
    }

    @Override
    public Attribute<Node> lastChild() {
        return null;
    }

    @Override
    public Attribute<Node> previousSibling() {
        return null;
    }

    @Override
    public Attribute<Node> nextSibling() {
        return null;
    }

    @Override
    public Attribute<NamedNodeMap> attributes() {
        return null;
    }

    @Override
    public Attribute<Document> ownerDocument() {
        return null;
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        return null;
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        return null;
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException {
        return null;
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public Node cloneNode(boolean deep) {
        return null;
    }

    @Override
    public void normalize() {

    }

    @Override
    public boolean isSupported(DOMString feature, DOMString version) {
        return false;
    }

    @Override
    public Attribute<DOMString> namespaceURI() {
        return null;
    }

    @Override
    public Attribute<DOMString> prefix() {
        return null;
    }

    @Override
    public Attribute<DOMString> localName() {
        return null;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public Attribute<DOMString> baseURI() {
        return null;
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        return 0;
    }

    @Override
    public Attribute<DOMString> textContent() {
        return super.textContent();
    }

    @Override
    public boolean isSameNode(Node other) {
        return false;
    }

    @Override
    public DOMString lookupPrefix(DOMString namespaceURI) {
        return null;
    }

    @Override
    public boolean isDefaultNamespace(DOMString namespaceURI) {
        return false;
    }

    @Override
    public DOMString lookupNamespaceURI(DOMString prefix) {
        return null;
    }

    @Override
    public boolean isEqualNode(Node arg) {
        return false;
    }

    @Override
    public DOMObject getFeature(DOMString feature, DOMString version) {
        return null;
    }

    @Override
    public DOMUserData setUserData(DOMString key, DOMUserData data, UserDataHandler handler) {
        return null;
    }

    @Override
    public DOMUserData getUserData(DOMString key) {
        return null;
    }
}
