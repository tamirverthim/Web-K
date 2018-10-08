package com.earnix.kbrowser.script.whatwg_dom.impl;

import com.earnix.kbrowser.dom.select.Elements;
import com.earnix.kbrowser.script.Binder;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.whatwg_dom.Element;
import com.earnix.kbrowser.script.whatwg_dom.HTMLCollection;
import com.earnix.kbrowser.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

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

    public Elements getModel() {
        return elements;
    }
}
