package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.Attr;
import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.Element;
import org.xhtmlrenderer.js.dom.TypeInfo;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class AttrImpl implements Attr {
    @Override
    public Attribute<DOMString> name() {
        return null;
    }

    @Override
    public Attribute<Boolean> specified() {
        return null;
    }

    @Override
    public Attribute<DOMString> value() {
        return null;
    }

    @Override
    public Attribute<Element> ownerElement() {
        return null;
    }

    @Override
    public Attribute<TypeInfo> schemaTypeInfo() {
        return null;
    }

    @Override
    public Attribute<Boolean> isId() {
        return null;
    }
}
