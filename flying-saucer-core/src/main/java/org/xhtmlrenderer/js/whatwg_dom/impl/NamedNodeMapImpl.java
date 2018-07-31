package org.xhtmlrenderer.js.whatwg_dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.jsoup.nodes.Element;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.whatwg_dom.Attr;
import org.xhtmlrenderer.js.whatwg_dom.NamedNodeMap;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NamedNodeMapImpl implements NamedNodeMap {
    
    Element modelElement;

    public NamedNodeMapImpl(Element modelElement) {
        this.modelElement = modelElement;
    }

    @Override
    public int length() {
        return modelElement.attributes().size();
    }
    
    @Override
    public Attr item(int index) {
        val attribute = modelElement.attributes().asList().get(index);
        return new AttrImpl(modelElement, attribute, panel);
    }

    @Override
    public Attr getNamedItem(DOMString qualifiedName) {
        val attribute = modelElement.attributes().asList().stream().filter(a -> a.getKey().equals(qualifiedName.toString())).findFirst();
        return attribute.map(attribute1 -> new AttrImpl(modelElement, attribute1, panel)).orElse(null);
    }

    @Override
    public Attr getNamedItemNS(DOMString namespace, DOMString localName) {
        return null;
    }

    @Override
    public Attr setNamedItem(Attr attr) {
        return null;
    }

    @Override
    public Attr setNamedItemNS(Attr attr) {
        return null;
    }

    @Override
    public Attr removeNamedItem(DOMString qualifiedName) {
        return null;
    }

    @Override
    public Attr removeNamedItemNS(DOMString namespace, DOMString localName) {
        return null;
    }

    @Override
    public Object namedItem(DOMString name) {
        return getNamedItem(name);
    }
}
