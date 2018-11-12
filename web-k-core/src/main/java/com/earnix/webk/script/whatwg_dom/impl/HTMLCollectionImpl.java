package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.select.Elements;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.HTMLCollection;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HTMLCollectionImpl implements HTMLCollection {

    Elements elements;
    ScriptContext ctx;

    public HTMLCollectionImpl(Elements elements) {
        this.elements = elements;
        this.ctx = ctx;
    }

    @Override
    public int length() {
        return elements.size();
    }

    @Override
    public Element item(int index) {
        return ScriptDOMFactory.getElement(elements.get(index));
    }

    @Override
    public Element namedItem(@DOMString String name) {
        return null;
    }

    public Elements getModel() {
        return elements;
    }
}
