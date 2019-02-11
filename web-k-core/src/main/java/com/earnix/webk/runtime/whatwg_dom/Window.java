package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Partial;
import com.earnix.webk.runtime.web_idl.Readonly;
import com.earnix.webk.runtime.web_idl.Replaceable;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
@Partial
public interface Window {
    
    @Replaceable
    @Readonly
    Attribute<Object> event();
    
}
