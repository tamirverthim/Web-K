package org.xhtmlrenderer.script.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.dom.nodes.Node;
import org.xhtmlrenderer.script.Binder;
import org.xhtmlrenderer.script.whatwg_dom.Element;
import org.xhtmlrenderer.script.whatwg_dom.NonDocumentTypeChildNode;
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
            return (Element) Binder.get(((org.xhtmlrenderer.dom.nodes.Element) target).previousElementSibling(), panel);
        }
        
        return null;
    }

    @Override
    public Element nextElementSibling() {
        if(target instanceof Element){
            return (Element) Binder.get(((org.xhtmlrenderer.dom.nodes.Element) target).nextElementSibling(), panel);
        }
        return null;    
    }
}
