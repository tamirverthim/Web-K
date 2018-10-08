package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Mixin;
import com.earnix.webk.script.web_idl.Optional;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Mixin
public interface NonElementParentNode {
    @Optional
    Element getElementById(@DOMString String elementId);
}
