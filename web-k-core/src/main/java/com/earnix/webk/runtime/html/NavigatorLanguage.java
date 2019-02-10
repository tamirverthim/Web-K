package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.FrozenArray;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface NavigatorLanguage {
    
    @ReadonlyAttribute
    @DOMString String language();

    @ReadonlyAttribute
    @DOMString FrozenArray<@DOMString String> languages();
    
}
