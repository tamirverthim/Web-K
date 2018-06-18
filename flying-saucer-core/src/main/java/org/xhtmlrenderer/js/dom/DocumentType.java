package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface DocumentType {
    
    @Readonly
    Attribute<DOMString> name();

    @Readonly
    Attribute<NamedNodeMap> entities();

    @Readonly
    Attribute<NamedNodeMap> notations();

    // Introduced in DOM Level 2:
    @Readonly
    Attribute<DOMString> publicId();

    // Introduced in DOM Level 2:
    @Readonly
    Attribute<DOMString> systemId();

    // Introduced in DOM Level 2:
    @Readonly
    Attribute<DOMString> internalSubset();
    
}
