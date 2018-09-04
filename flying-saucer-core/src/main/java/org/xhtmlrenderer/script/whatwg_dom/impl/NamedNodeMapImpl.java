package org.xhtmlrenderer.script.whatwg_dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.dom.nodes.Element;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.whatwg_dom.Attr;
import org.xhtmlrenderer.script.whatwg_dom.NamedNodeMap;
import org.xhtmlrenderer.swing.BasicPanel;

/**
 * @author Taras Maslov
 * 7/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NamedNodeMapImpl implements NamedNodeMap {
    
    Element modelElement;
    BasicPanel panel;

    public NamedNodeMapImpl(Element modelElement, BasicPanel panel) {
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
