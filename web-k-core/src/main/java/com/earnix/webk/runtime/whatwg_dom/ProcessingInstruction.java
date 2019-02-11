package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface ProcessingInstruction {
    @ReadonlyAttribute
    @DOMString String target();
}
