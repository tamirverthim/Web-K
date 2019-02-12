package com.earnix.webk.runtime.dom.impl;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.dom.DOMImplementation;
import com.earnix.webk.runtime.dom.Document;
import com.earnix.webk.runtime.dom.DocumentType;
import com.earnix.webk.runtime.dom.XMLDocument;

/**
 * @author Taras Maslov
 * 7/3/2018
 */
public class DOMImplementationImpl implements DOMImplementation {

    @Override
    public DocumentType createDocumentType(@DOMString String qualifiedName, @DOMString String publicId, @DOMString String systemId) {
        return null;
    }

    @Override
    public XMLDocument createDocument(@DOMString String namespace, @DOMString String qualifiedName, DocumentType doctype) {
        return null;
    }

    @Override
    public Document createHTMLDocument(@DOMString String title) {
        return null;
    }

    @Override
    public boolean hasFeature() {
        return false;
    }

}
