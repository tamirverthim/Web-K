package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.AttributeModel;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.Binder;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.whatwg_dom.Attr;
import com.earnix.webk.script.whatwg_dom.Document;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventListener;
import com.earnix.webk.script.whatwg_dom.GetRootNodeOptions;
import com.earnix.webk.script.whatwg_dom.Node;
import com.earnix.webk.script.whatwg_dom.NodeList;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttrImpl implements Attr {

    AttributeModel model;
    ElementModel modelNode;
    BasicPanel panel;


    public AttrImpl(ElementModel modelNode, AttributeModel attribute, BasicPanel panel) {
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
    public com.earnix.webk.script.web_idl.Attribute<String> value() {
        return new com.earnix.webk.script.web_idl.Attribute<String>() {
            @Override
            public String get() {
                return model.getValue();
            }

            @Override
            public void set(String string) {
                modelNode.attr(model.getKey(), string);
                model.setValue(string);
            }
        };
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Element ownerElement() {
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
    public @USVString
    String baseURI() {
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
    public com.earnix.webk.script.whatwg_dom.Element parentElement() {
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
    public com.earnix.webk.script.web_idl.Attribute<String> nodeValue() {
        return new com.earnix.webk.script.web_idl.Attribute<String>() {
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
    public com.earnix.webk.script.web_idl.Attribute<String> textContent() {
        return nodeValue();
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

    @Override
    public String toString() {
        return model.getKey() + "=" + model.getValue();
    }
}
