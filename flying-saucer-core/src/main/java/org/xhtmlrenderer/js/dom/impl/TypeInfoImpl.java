package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.TypeInfo;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class TypeInfoImpl implements TypeInfo {
    @Override
    public Attribute<DOMString> typeName() {
        return null;
    }

    @Override
    public Attribute<DOMString> typeNamespace() {
        return null;
    }

    @Override
    public boolean isDerivedFrom(DOMString typeNamespaceArg, DOMString typeNameArg, long derivationMethod) {
        return false;
    }
}
