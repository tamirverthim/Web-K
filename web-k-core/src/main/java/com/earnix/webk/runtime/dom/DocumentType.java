package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

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
