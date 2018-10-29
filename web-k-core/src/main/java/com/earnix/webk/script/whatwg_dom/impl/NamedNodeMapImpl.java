package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Attr;
import com.earnix.webk.script.whatwg_dom.NamedNodeMap;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * @author Taras Maslov
 * 7/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NamedNodeMapImpl implements NamedNodeMap {

    ElementModel modelElement;
    BasicPanel panel;

    public NamedNodeMapImpl(ElementModel modelElement, BasicPanel panel) {
        this.modelElement = modelElement;
        this.panel = panel;
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
    public Attr getNamedItem(@DOMString String qualifiedName) {
        val attribute = modelElement.attributes().asList().stream().filter(a -> a.getKey().equals(qualifiedName.toString())).findFirst();
        return attribute.map(attribute1 -> new AttrImpl(modelElement, attribute1, panel)).orElse(null);
    }

    @Override
    public Attr getNamedItemNS(@DOMString String namespace, @DOMString String localName) {
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
    public Attr removeNamedItem(@DOMString String qualifiedName) {
        return null;
    }

    @Override
    public Attr removeNamedItemNS(@DOMString String namespace, @DOMString String localName) {
        return null;
    }

    @Override
    public Object namedItem(@DOMString String name) {
        return getNamedItem(name);
    }
}
