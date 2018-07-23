package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Node;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.NonDocumentTypeChildNode;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NonDocumentTypeChildNodeImpl implements NonDocumentTypeChildNode {
    
    Node target;
    XHTMLPanel panel;
    
    public NonDocumentTypeChildNodeImpl(Node target, XHTMLPanel panel) {
        this.target = target;
        this.panel = panel;
    }

    @Override
    public Element previousElementSibling() {
        if(target instanceof Element){
            return (Element) Binder.get(((org.jsoup.nodes.Element) target).previousElementSibling(), panel);
        }
        
        return null;
    }

    @Override
    public Element nextElementSibling() {
        if(target instanceof Element){
            return (Element) Binder.get(((org.jsoup.nodes.Element) target).nextElementSibling(), panel);
        }
        return null;    
    }
}
