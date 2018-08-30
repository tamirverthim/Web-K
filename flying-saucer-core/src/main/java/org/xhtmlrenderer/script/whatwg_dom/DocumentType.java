package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface DocumentType extends Node {
    
    @ReadonlyAttribute
    @DOMString 
    String name();

    @ReadonlyAttribute
    @DOMString 
    String publicId();

    @ReadonlyAttribute
    @DOMString 
    String systemId();
    
}
