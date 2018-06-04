package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.DocumentType;
import org.xhtmlrenderer.js.dom.NamedNodeMap;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * Ready
 * @author Taras Maslov
 * 6/4/2018
 */
public class DocumentTypeImpl implements DocumentType {
    
    private org.w3c.dom.DocumentType target;

    public DocumentTypeImpl(org.w3c.dom.DocumentType target) {
        this.target = target;
    }

    @Override
    public Attribute<DOMString> name() {
        return Attribute.<DOMString>readOnly().give(() -> new DOMStringImpl(target.getName()));
    }

    @Override
    public Attribute<NamedNodeMap> entities() {
        return Attribute.<NamedNodeMap>readOnly().give(() -> new NamedNodeMapImpl(target.getEntities()));
    }

    @Override
    public Attribute<NamedNodeMap> notations() {
        return Attribute.<NamedNodeMap>readOnly().give(() -> new NamedNodeMapImpl(target.getNotations()));
    }

    @Override
    public Attribute<DOMString> publicId() {
        return Attribute.<DOMString>readOnly().give(() -> new DOMStringImpl(target.getPublicId()));
    }

    @Override
    public Attribute<DOMString> systemId() {
        return Attribute.<DOMString>readOnly().give(() -> new DOMStringImpl(target.getSystemId()));
    }

    @Override
    public Attribute<DOMString> internalSubset() {
        return Attribute.<DOMString>readOnly().give(() -> new DOMStringImpl(target.getInternalSubset()));
    }
}
