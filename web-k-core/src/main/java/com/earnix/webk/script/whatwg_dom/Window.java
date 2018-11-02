package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.Partial;
import com.earnix.webk.script.web_idl.Readonly;
import com.earnix.webk.script.web_idl.Replaceable;

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
