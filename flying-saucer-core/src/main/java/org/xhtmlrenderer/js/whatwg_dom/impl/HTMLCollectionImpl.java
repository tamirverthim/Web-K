package org.xhtmlrenderer.js.whatwg_dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jsoup.select.Elements;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.HTMLCollection;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HTMLCollectionImpl implements HTMLCollection {
    
    Elements elements;

    public HTMLCollectionImpl(Elements elements) {
        this.elements = elements;
    }

    @Override
    public int length() {
        return elements.size();
    }

    @Override
    public Element item(int index) {
        return Binder.getElement(elements.get(index));
    }

    @Override
    public Element namedItem(DOMString name) {
        return null;
    }
}
