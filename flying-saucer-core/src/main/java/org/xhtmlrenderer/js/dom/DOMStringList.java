package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface DOMStringList {
    
    DOMString item(@Unsigned long index);
    
    @Unsigned @Readonly
    Attribute<Long> length();
    
    boolean contains(DOMString str);
    
}
