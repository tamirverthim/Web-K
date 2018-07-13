package org.xhtmlrenderer.js.impl;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.js.whatwg_dom.*;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
public class NodeImpl implements Node {
    
    @Override
    public short nodeType() {
        return 0;
    }

    @Override
    public DOMString nodeName() {
        return null;
    }

    @Override
    public USVString baseURI() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public Document ownerDocument() {
        return null;
    }

    @Override
    public Node getRootNode(GetRootNodeOptions options) {
        return null;
    }

    @Override
    public Node parentNode() {
        return null;
    }

    @Override
    public Element parentElement() {
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public NodeList childNodes() {
        return null;
    }

    @Override
    public Node firstChild() {
        return null;
    }

    @Override
    public Node lastChild() {
        return null;
    }

    @Override
    public Node previousSibling() {
        return null;
    }

    @Override
    public Node nextSibling() {
        return null;
    }

    @Override
    public Attribute<DOMString> nodeValue() {
        return null;
    }

    @Override
    public Attribute<DOMString> textContent() {
        return null;
    }

    @Override
    public void normalize() {

    }

    @Override
    public Node cloneNode(boolean deep) {
        return null;
    }

    @Override
    public boolean isEqualNode(Node otherNode) {
        return false;
    }

    @Override
    public boolean isSameNode(Node otherNode) {
        return false;
    }

    @Override
    public short compareDocumentPosition(Node other) {
        return 0;
    }

    @Override
    public boolean contains(Node other) {
        return false;
    }

    @Override
    public DOMString lookupPrefix(DOMString namespace) {
        return null;
    }

    @Override
    public DOMString lookupNamespaceURI(DOMString prefix) {
        return null;
    }

    @Override
    public boolean isDefaultNamespace(DOMString namespace) {
        return false;
    }

    @Override
    public Node insertBefore(Node node, Node child) {
        return null;
    }

    @Override
    public Node appendChild(Node node) {
        return null;
    }

    @Override
    public Node replaceChild(Node node, Node child) {
        return null;
    }

    @Override
    public Node removeChild(Node child) {
        return null;
    }
}
