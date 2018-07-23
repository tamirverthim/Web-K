package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.js.html5.canvas.HTMLSlotElement;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Sequence;
import org.xhtmlrenderer.js.whatwg_dom.*;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElementImpl extends NodeImpl implements Element {
    
    final org.jsoup.nodes.Element target;
    
    final ChildNodeImpl childNodeMixin;

    public ElementImpl(org.jsoup.nodes.Element target, XHTMLPanel panel) {
        super(target, panel);
        this.target = target;
        childNodeMixin = new ChildNodeImpl(target);
    }

    @Override
    public DOMString namespaceURI() {
        return null;
    }

    @Override
    public DOMString prefix() {
        return null;
    }

    @Override
    public DOMString localName() {
        return null;
    }

    @Override
    public DOMString tagName() {
        return null;
    }

    @Override
    public Attribute<DOMString> id() {
        return null;
    }

    @Override
    public Attribute<DOMString> className() {
        return null;
    }

    @Override
    public DOMTokenList classList() {
        return null;
    }

    @Override
    public Attribute<DOMString> slot() {
        return null;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public NamedNodeMap attributes() {
        return null;
    }

    @Override
    public Sequence<DOMString> getAttributeNames() {
        return null;
    }

    @Override
    public DOMString getAttribute(DOMString qualifiedName) {
        return null;
    }

    @Override
    public DOMString getAttributeNS(DOMString namespace, DOMString localName) {
        return null;
    }

    @Override
    public void setAttribute(DOMString qualifiedName, DOMString value) {

    }

    @Override
    public void setAttributeNS(DOMString namespace, DOMString qualifiedName, DOMString value) {

    }

    @Override
    public void removeAttribute(DOMString qualifiedName) {

    }

    @Override
    public void removeAttributeNS(DOMString namespace, DOMString localName) {

    }

    @Override
    public boolean toggleAttribute(DOMString qualifiedName, Boolean force) {
        return false;
    }

    @Override
    public boolean hasAttribute(DOMString qualifiedName) {
        return false;
    }

    @Override
    public boolean hasAttributeNS(DOMString namespace, DOMString localName) {
        return false;
    }

    @Override
    public Attr getAttributeNode(DOMString qualifiedName) {
        return null;
    }

    @Override
    public Attr getAttributeNodeNS(DOMString namespace, DOMString localName) {
        return null;
    }

    @Override
    public Attr setAttributeNode(Attr attr) {
        return null;
    }

    @Override
    public Attr setAttributeNodeNS(Attr attr) {
        return null;
    }

    @Override
    public Attr removeAttributeNode(Attr attr) {
        return null;
    }

    @Override
    public ShadowRoot attachShadow(ShadowRootInit init) {
        return null;
    }

    @Override
    public ShadowRoot shadowRoot() {
        return null;
    }

    @Override
    public Element closest(DOMString selectors) {
        return null;
    }

    @Override
    public boolean matches(DOMString selectors) {
        return false;
    }

    @Override
    public boolean webkitMatchesSelector(DOMString selectors) {
        return false;
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
    public Element insertAdjacentElement(DOMString where, Element element) {
        return null;
    }

    @Override
    public void insertAdjacentText(DOMString where, DOMString data) {

    }

    // region ChildNode
    
    @Override
    public void before(Object... nodes) {
        childNodeMixin.before(nodes);
    }

    @Override
    public void after(Object... nodes) {
        childNodeMixin.after(nodes);
    }

    @Override
    public void replaceWith(Object... nodes) {
        childNodeMixin.replaceWith(nodes);
    }

    @Override
    public void remove() {
        childNodeMixin.remove();
    }
    
    // endregion

    @Override
    public Element previousElementSibling() {
        return null;
    }

    @Override
    public Element nextElementSibling() {
        return null;
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

    @Override
    public HTMLSlotElement assignedSlot() {
        return null;
    }
}
