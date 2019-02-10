package com.earnix.webk.runtime.whatwg_dom.impl;

import com.earnix.webk.runtime.whatwg_dom.impl.nodes.AttributeModel;
import com.earnix.webk.runtime.ScriptContext;
import com.earnix.webk.runtime.impl.ElementImpl;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.USVString;
import com.earnix.webk.runtime.whatwg_dom.Attr;
import com.earnix.webk.runtime.whatwg_dom.Document;
import com.earnix.webk.runtime.whatwg_dom.EventTarget;
import com.earnix.webk.runtime.whatwg_dom.GetRootNodeOptions;
import com.earnix.webk.runtime.whatwg_dom.Node;
import com.earnix.webk.runtime.whatwg_dom.NodeList;
import lombok.AccessLevel;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttrImpl implements Attr {

    AttributeModel model;
    ElementImpl modelNode;
    ScriptContext scriptContext;
    @Delegate(types = {EventTarget.class})
    EventTargetImpl eventTargetImpl;

    public AttrImpl(ElementImpl modelNode, AttributeModel attribute, ScriptContext scriptContext) {
        super();
        this.modelNode = modelNode;
        this.model = attribute;
        this.scriptContext = scriptContext;
        eventTargetImpl = new EventTargetImpl(() ->scriptContext);
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
    public com.earnix.webk.runtime.web_idl.Attribute<String> value() {
        return new com.earnix.webk.runtime.web_idl.Attribute<String>() {
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
    public com.earnix.webk.runtime.whatwg_dom.Element ownerElement() {
        return modelNode;
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
    public com.earnix.webk.runtime.whatwg_dom.Element parentElement() {
        return modelNode.parent();
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
    public com.earnix.webk.runtime.web_idl.Attribute<String> nodeValue() {
        return new com.earnix.webk.runtime.web_idl.Attribute<String>() {
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
    public com.earnix.webk.runtime.web_idl.Attribute<String> textContent() {
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
    public String toString() {
        return model.getKey() + "=" + model.getValue();
    }
}
