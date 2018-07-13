package org.xhtmlrenderer.js.impl;

import org.jsoup.nodes.Node;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.NonDocumentTypeChildNode;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
public class NonDocumentTypeChildNodeImpl implements NonDocumentTypeChildNode {
    
    private Node target;

    public NonDocumentTypeChildNodeImpl(Node target) {
        this.target = target;
    }

    @Override
    public Element previousElementSibling() {
        if(target instanceof Element){
            return (Element) Binder.get(((org.jsoup.nodes.Element) target).previousElementSibling());
        }
        
        return null;
    }

    @Override
    public Element nextElementSibling() {
        if(target instanceof Element){
            return (Element) Binder.get(((org.jsoup.nodes.Element) target).nextElementSibling());
        }
        return null;    
    }
}
