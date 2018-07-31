package org.xhtmlrenderer.js.whatwg_dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.impl.DOMStringImpl;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.js.whatwg_dom.*;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttrImpl implements Attr  {
    
    org.jsoup.nodes.Attribute model;
    org.jsoup.nodes.Element modelNode;
    XHTMLPanel panel;
    
    
    public AttrImpl(org.jsoup.nodes.Element modelNode, org.jsoup.nodes.Attribute attribute, XHTMLPanel panel) {
        super();
        this.modelNode = modelNode;
        this.model = attribute;
        this.panel = panel;
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
    public DOMString name() {
        return DOMStringImpl.of(model.getKey());
    }

    @Override
    public Attribute<DOMString> value() {
        return new Attribute<DOMString>() {
            @Override
            public DOMString get() {
                return DOMStringImpl.of(model.getValue());
            }

            @Override
            public void set(DOMString string) {
                modelNode.attr(model.getKey(), string.toString());
                model.setValue(string.toString());
            }
        };
    }

    @Override
    public Element ownerElement() {
        return Binder.getElement(modelNode, panel);
    }

    @Override
    public boolean specified() {
        return false;
    }

    @Override
    public short nodeType() {
        return ATTRIBUTE_NODE;
    }

    @Override
    public DOMString nodeName() {
        return DOMStringImpl.of(model.getKey());
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
    public org.xhtmlrenderer.js.whatwg_dom.Node getRootNode(GetRootNodeOptions options) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node parentNode() {
        return null;
    }

    @Override
    public Element parentElement() {
        return Binder.getElement(modelNode.parent(), panel);
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
    public org.xhtmlrenderer.js.whatwg_dom.Node firstChild() {
        return null;
    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node lastChild() {
        return null;
    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node previousSibling() {
        return null;
    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node nextSibling() {
        return null;
    }

    @Override
    public Attribute<DOMString> nodeValue() {
        return new Attribute<DOMString>(){
            @Override
            public DOMString get() {
                return DOMStringImpl.of(model.getValue());
            }

            @Override
            public void set(DOMString o) {
                modelNode.attr(model.getKey(), o.toString());
                model.setValue(o.toString());
            }
        };
    }

    @Override
    public Attribute<DOMString> textContent() {
        return nodeValue();
    }

    @Override
    public void normalize() {

    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node cloneNode(boolean deep) {
        return null;
    }

    @Override
    public boolean isEqualNode(org.xhtmlrenderer.js.whatwg_dom.Node otherNode) {
        return false;
    }

    @Override
    public boolean isSameNode(org.xhtmlrenderer.js.whatwg_dom.Node otherNode) {
        return false;
    }

    @Override
    public short compareDocumentPosition(org.xhtmlrenderer.js.whatwg_dom.Node other) {
        return 0;
    }

    @Override
    public boolean contains(org.xhtmlrenderer.js.whatwg_dom.Node other) {
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
    public org.xhtmlrenderer.js.whatwg_dom.Node insertBefore(org.xhtmlrenderer.js.whatwg_dom.Node node, org.xhtmlrenderer.js.whatwg_dom.Node child) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node appendChild(org.xhtmlrenderer.js.whatwg_dom.Node node) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node replaceChild(org.xhtmlrenderer.js.whatwg_dom.Node node, org.xhtmlrenderer.js.whatwg_dom.Node child) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.js.whatwg_dom.Node removeChild(org.xhtmlrenderer.js.whatwg_dom.Node child) {
        return null;
    }

    @Override
    public void addEventListener(DOMString type, EventListener callback, Object options) {

    }

    @Override
    public void removeEventListener(DOMString type, EventListener callback, Object options) {

    }

    @Override
    public boolean dispatchEvent(Event event) {
        return false;
    }
}
