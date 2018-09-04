package org.xhtmlrenderer.script.whatwg_dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.dom.select.Elements;
import org.xhtmlrenderer.script.Binder;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.whatwg_dom.Element;
import org.xhtmlrenderer.script.whatwg_dom.HTMLCollection;
import org.xhtmlrenderer.swing.BasicPanel;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HTMLCollectionImpl implements HTMLCollection {
    
    Elements elements;
    BasicPanel panel;
    
    public HTMLCollectionImpl(Elements elements, BasicPanel panel) {
        this.elements = elements;
        this.panel = panel;
    }  

    @Override
    public int length() {
        return elements.size();
    }

    @Override
    public Element item(int index) {
        return Binder.getElement(elements.get(index), panel);
    }

    @Override
    public Element namedItem(@DOMString String name) {
        return null;
    }
    
    public Elements getModel(){
        return elements;
    }
}
