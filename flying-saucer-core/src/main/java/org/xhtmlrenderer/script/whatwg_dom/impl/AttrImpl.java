package org.xhtmlrenderer.script.whatwg_dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.script.Binder;
import org.xhtmlrenderer.script.web_idl.Attribute;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.USVString;
import org.xhtmlrenderer.script.whatwg_dom.*;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttrImpl implements Attr  {
    
    org.xhtmlrenderer.dom.nodes.Attribute model;
    org.xhtmlrenderer.dom.nodes.Element modelNode;
    XHTMLPanel panel;
    
    
    public AttrImpl(org.xhtmlrenderer.dom.nodes.Element modelNode, org.xhtmlrenderer.dom.nodes.Attribute attribute, XHTMLPanel panel) {
        super();
        this.modelNode = modelNode;
        this.model = attribute;
        this.panel = panel;
    }


    @Override
    public @DOMString String namespaceURI() {
        return null;
    }

    @Override
    public @DOMString String prefix() {
        return null;
    }

    @Override
    public @DOMString String localName() {
        return null;
    }

    @Override
    public @DOMString String name() {
        return model.getKey();
    }

    @Override
    public Attribute<String> value() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return model.getValue();
            }

            @Override
            public void set(String string) {
                modelNode.attr(model.getKey(), string.toString());
                model.setValue(string);
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
    public String nodeName() {
        return model.getKey();
    }

    @Override
    public @USVString String baseURI() {
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
    public org.xhtmlrenderer.script.whatwg_dom.Node getRootNode(GetRootNodeOptions options) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node parentNode() {
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
    public org.xhtmlrenderer.script.whatwg_dom.Node firstChild() {
        return null;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node lastChild() {
        return null;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node previousSibling() {
        return null;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node nextSibling() {
        return null;
    }

    @Override
    public Attribute<String> nodeValue() {
        return new Attribute<String>(){
            @Override
            public String get() {
                return model.getValue();
            }

            @Override
            public void set(String o) {
                modelNode.attr(model.getKey(), o);
                model.setValue(o);
            }
        };
    }

    @Override
    public Attribute<String> textContent() {
        return nodeValue();
    }

    @Override
    public void normalize() {

    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node cloneNode(boolean deep) {
        return null;
    }

    @Override
    public boolean isEqualNode(org.xhtmlrenderer.script.whatwg_dom.Node otherNode) {
        return false;
    }

    @Override
    public boolean isSameNode(org.xhtmlrenderer.script.whatwg_dom.Node otherNode) {
        return false;
    }

    @Override
    public short compareDocumentPosition(org.xhtmlrenderer.script.whatwg_dom.Node other) {
        return 0;
    }

    @Override
    public boolean contains(org.xhtmlrenderer.script.whatwg_dom.Node other) {
        return false;
    }

    @Override
    public @DOMString String lookupPrefix(@DOMString String namespace) {
        return null;
    }

    @Override
    public @DOMString String lookupNamespaceURI(@DOMString String prefix) {
        return null;
    }

    @Override
    public boolean isDefaultNamespace(@DOMString String namespace) {
        return false;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node insertBefore(org.xhtmlrenderer.script.whatwg_dom.Node node, org.xhtmlrenderer.script.whatwg_dom.Node child) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node appendChild(org.xhtmlrenderer.script.whatwg_dom.Node node) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node replaceChild(org.xhtmlrenderer.script.whatwg_dom.Node node, org.xhtmlrenderer.script.whatwg_dom.Node child) {
        return null;
    }

    @Override
    public org.xhtmlrenderer.script.whatwg_dom.Node removeChild(org.xhtmlrenderer.script.whatwg_dom.Node child) {
        return null;
    }

    @Override
    public void addEventListener(@DOMString String type, EventListener callback, Object options) {

    }

    @Override
    public void removeEventListener(@DOMString String type, EventListener callback, Object options) {

    }

    @Override
    public boolean dispatchEvent(Event event) {
        return false;
    }
}
