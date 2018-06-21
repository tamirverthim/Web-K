package org.xhtmlrenderer.js.impl;

import lombok.val;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.js.whatwg_dom.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public class DocumentImpl implements Document {
    
    org.jsoup.nodes.Document document;

    public DocumentImpl(org.jsoup.nodes.Document document) {
        this.document = document;
    }

    @Override
    public DOMImplementation implementation() {
        return null;
    }

    @Override
    public USVString URL() {
        return null;
    }

    @Override
    public USVString documentURI() {
        return null;
    }

    @Override
    public USVString origin() {
        return null;
    }

    @Override
    public DOMString compatMode() {
        return null;
    }

    @Override
    public DOMString characterSet() {
        return null;
    }

    @Override
    public DOMString charset() {
        return null;
    }

    @Override
    public DOMString inputEncoding() {
        return null;
    }

    @Override
    public DOMString contentType() {
        return null;
    }

    @Override
    public DocumentType doctype() {
        return null;
    }

    @Override
    public Element documentElement() {
        return null;
    }

    @Override
    public HTMLCollection getElementsByTagName(DOMString qualifiedName) {
        return null;
    }

    @Override
    public HTMLCollection getElementsByTagNameNS(DOMString namespace, DOMString localName) {
        return null;
    }

    @Override
    public HTMLCollection getElementsByClassName(DOMString classNames) {
        return null;
    }

    @Override
    public Element createElement(DOMString localName, Object options) {
        return null;
    }

    @Override
    public Element createElementNS(DOMString namespace, DOMString qualifiedName, Object options) {
        return null;
    }

    @Override
    public DocumentFragment createDocumentFragment() {
        return null;
    }

    @Override
    public Text createTextNode(DOMString data) {
        return null;
    }

    @Override
    public CDATASection createCDATASection(DOMString data) {
        return null;
    }

    @Override
    public Comment createComment(DOMString data) {
        return null;
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(DOMString target, DOMString data) {
        return null;
    }

    @Override
    public Node importNode(Node node, boolean deep) {
        return null;
    }

    @Override
    public Node adoptNode(Node node) {
        return null;
    }

    @Override
    public Attr createAttribute(DOMString localName) {
        return null;
    }

    @Override
    public Attr createAttributeNS(DOMString namespace, DOMString qualifiedName) {
        return null;
    }

    @Override
    public Event createEvent(DOMString inter_face) {
        return null;
    }

    @Override
    public Range createRange() {
        return null;
    }

    @Override
    public NodeIterator createNodeIterator(Node root, long whatToShow, NodeFilter filter) {
        return null;
    }

    @Override
    public TreeWalker createTreeWalker(Node root, long whatToShow, NodeFilter filter) {
        return null;
    }

    @Override
    public Element getElementById(DOMString elementId) {
        val jsoupEl = document.getElementById(elementId.toString());
        if(Binder.get(jsoupEl) != null){
            return Binder.get(jsoupEl);
        }
        if(jsoupEl.nodeName().equalsIgnoreCase("canvas")){
            return new HTMLCanvasElementImpl(
                    jsoupEl,
                    Integer.parseInt(jsoupEl.attr("width")), 
                    Integer.parseInt(jsoupEl.attr("height"))
            );
        }
        return new ElementImpl(jsoupEl);
    }

    @Override
    public Attribute<HTMLCollection> children() {
        return null;
    }

    @Override
    public Attribute<Element> firstElementChild() {
        return null;
    }

    @Override
    public Attribute<Element> lastElementChild() {
        return null;
    }

    @Override
    public Attribute<Long> childElementCount() {
        return null;
    }

    @Override
    public void prepend(Object... nodes) {

    }

    @Override
    public void append(Object... nodes) {

    }

    @Override
    public Element querySelector(DOMString selectors) {
        return null;
    }

    @Override
    public NodeList querySelectorAll(DOMString selectors) {
        return null;
    }
}
