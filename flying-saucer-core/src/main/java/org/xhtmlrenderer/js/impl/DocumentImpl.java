package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.js.whatwg_dom.*;
import org.xhtmlrenderer.js.whatwg_dom.impl.HTMLCollectionImpl;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentImpl implements Document {
    
    org.jsoup.nodes.Document document;
    DOMImplementation implementation = new DOMImplementationImpl();
    
    XHTMLPanel panel;
    
    public DocumentImpl(XHTMLPanel panel) {
        this.document = panel.getDocument();
    }

    @Override
    public DOMImplementation implementation() {
        return implementation;
    }

    @Override
    public USVString URL() {
        return USVStringImpl.of(panel.getURL().toString());
    }

    @Override
    public USVString documentURI() {
        return USVStringImpl.of(panel.getURL().toString());
    }

    @Override
    public USVString origin() {
        return null;
    }

    @Override
    public DOMString compatMode() {
        return DOMStringImpl.of("CSS1Compat");
    }

    @Override
    public DOMString characterSet() {
        return DOMStringImpl.of("UTF-8");
    }

    @Override
    public DOMString charset() {
        return DOMStringImpl.of("UTF8");
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
        return (Element) Binder.get(document, panel);
    }

    @Override
    public HTMLCollection getElementsByTagName(DOMString qualifiedName) {
        val elements = document.getElementsByTag(qualifiedName.toString());
        return new HTMLCollectionImpl(elements, panel);
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
        return new ElementImpl(new org.jsoup.nodes.Element(localName.toString()), panel);
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
        return (Element) Binder.get(jsoupEl, panel);
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
        val selected = document.select(selectors.toString());
        if(selected.size() > 0){
            Element bound = (Element) Binder.get(selected.first(), panel);
            if(bound == null){
                bound = new ElementImpl(selected.first(), panel);
                Binder.put(selected.first(), bound);
            }
            
            return bound;
        }
        return null;
    }

    @Override
    public NodeList querySelectorAll(DOMString selectors) {
        return null;
    }
}
