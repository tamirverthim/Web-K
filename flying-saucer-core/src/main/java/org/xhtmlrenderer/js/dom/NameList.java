package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface NameList {
    DOMString getName(@Unsigned long index);

    DOMString getNamespaceURI(@Unsigned long index);
    
    @Readonly
    @Unsigned
    Attribute<Long> length();

    boolean contains(DOMString str);

    boolean containsNS(DOMString namespaceURI,
                       DOMString name);
}
