package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.js.whatwg_dom.*;
import org.xhtmlrenderer.js.whatwg_dom.impl.EventImpl;
import org.xhtmlrenderer.simple.XHTMLPanel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class NodeImpl implements Node {
    
    org.jsoup.nodes.Node target;
    XHTMLPanel panel;

    public NodeImpl(org.jsoup.nodes.Node target, XHTMLPanel panel) {
        this.target = target;
        this.panel = panel;
    }

    @Override
    public short nodeType() {
        return 0;
    }

    @Override
    public DOMString nodeName() {
        return DOMStringImpl.of(target.nodeName());
    }

    @Override
    public USVString baseURI() {
        return USVStringImpl.of(panel.getURL().toString());
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public Document ownerDocument() {
        return (Document) Binder.get(target.ownerDocument(), panel);
    }

    @Override
    public Node getRootNode(GetRootNodeOptions options) {
        return Binder.get(target.root(), panel);
    }

    @Override
    public Node parentNode() {
        return Binder.get(target.parentNode(), panel);
    }

    @Override
    public Element parentElement() {
        val modelParent = target.parent();
        if(modelParent instanceof Element) {
            return Binder.getElement((org.jsoup.nodes.Element) modelParent, panel);
        } 
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public NodeList childNodes() {
        return new NodeListImpl(target.childNodes(), panel);
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
        NodeImpl impl = (NodeImpl) node;
        ((org.jsoup.nodes.Element)target).appendChild(impl.target);
//        panel.reloadDocument(panel.getDocument());
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
    
    // region EventTarget

    private LinkedHashMap<DOMString, List<EventListener>> listeners = new LinkedHashMap<>();
    
    @Override
    public void addEventListener(DOMString type, EventListener callback, Object options) { //callback is NashorJavaAdapter here, check if any adaptaion needed
        var typeListeners = listeners.computeIfAbsent(type, k -> new ArrayList<>());
        typeListeners.add(callback);
        
        // workaround for ChartJS
        if(type.toString().equals("animationstart")){
            dispatchEvent(new EventImpl(DOMStringImpl.of("animationstart"), null));
        }
    }

    @Override
    public void removeEventListener(DOMString type, EventListener callback, Object options) {
        val typeListeners = listeners.get(type);
        if(typeListeners != null){
            typeListeners.remove(callback);
            if(typeListeners.isEmpty()){
                listeners.remove(type);
            }
        }
    }

    @Override
    public boolean dispatchEvent(Event event) {
        val typeListeners = listeners.get(event.type());
        if(typeListeners != null) {
            typeListeners.forEach(l -> l.handleEvent(event));
            return true;
        }
        return false;
    }


    // endregion
}
