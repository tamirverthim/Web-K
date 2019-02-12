package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.Optional;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Mixin
public interface NonElementParentNode {
    @Optional
    Element getElementById(@DOMString String elementId);
}
