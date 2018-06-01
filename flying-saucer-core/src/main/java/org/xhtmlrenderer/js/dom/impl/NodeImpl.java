package org.xhtmlrenderer.js.dom.impl;

import lombok.val;
import org.xhtmlrenderer.js.dom.*;
import org.xhtmlrenderer.js.web_idl.Attribute;

import java.util.Objects;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
@SuppressWarnings("unchecked")
public class NodeImpl implements Node {
    
    protected org.w3c.dom.Node target;

    public NodeImpl(org.w3c.dom.Node target) {
        this.target = target;
    }

    @Override
    public Attribute<DOMString> nodeName() {
        return Attribute.readOnly().give(() -> new DOMStringImpl(target.getNodeName()));
    }

    @Override
    public Attribute<DOMString> nodeValue() throws DOMException {
        return Attribute.receive(v -> target.setNodeValue(v.toString()))
                .give(() -> new DOMStringImpl(target.getNodeValue()));
    }

    @Override
    public Attribute<Short> nodeType() {
        return Attribute.<Short>readOnly().give(() -> target.getNodeType());
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
        return null;
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
        val other = (NodeImpl)arg;
        return Objects.equals(target, other.target);
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
