package com.earnix.webk.script.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Document;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import com.earnix.webk.script.whatwg_dom.GetRootNodeOptions;
import com.earnix.webk.script.whatwg_dom.Node;
import com.earnix.webk.script.whatwg_dom.NodeList;
import com.earnix.webk.script.whatwg_dom.impl.EventTargetImpl;
import com.earnix.webk.script.whatwg_dom.impl.Level1EventTarget;
import com.earnix.webk.script.whatwg_dom.impl.ScriptDOMFactory;
import lombok.AccessLevel;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public abstract class NodeImpl implements Node {


    NodeModel target;
    
    protected ScriptContext ctx;
    
    @Delegate(types = {EventTarget.class})
    EventTargetImpl eventTargetImpl = new EventTargetImpl();

    protected Level1EventTarget level1EventTarget = new Level1EventTarget(eventTargetImpl);

    public NodeImpl(NodeModel target) {
        this.target = target;
    }

    @Override
    public short nodeType() {
        return 0;
    }

    @Override
    public String nodeName() {
        return target.nodeName();
    }

    @Override
    public String baseURI() {
        return ctx.getPanel().getURL().toString();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public Document ownerDocument() {
        return (Document) ScriptDOMFactory.get(target.ownerDocument());
    }

    @Override
    public Node getRootNode(GetRootNodeOptions options) {
        return ScriptDOMFactory.get(target.root());
    }

    @Override
    public Node parentNode() {
        return ScriptDOMFactory.get(target.parentNode());
    }

    @Override
    public com.earnix.webk.script.whatwg_dom.Element parentElement() {
        val modelParent = target.parent();
        if (modelParent instanceof ElementModel) {
            return ScriptDOMFactory.getElement((ElementModel) modelParent);
        }
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public NodeList childNodes() {
        return new NodeListImpl(target.childNodes());
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
    public Attribute<String> nodeValue() {
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
        NodeImpl impl = (NodeImpl) node;
        ((ElementModel) target).appendChild(impl.target);
        return node;
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
