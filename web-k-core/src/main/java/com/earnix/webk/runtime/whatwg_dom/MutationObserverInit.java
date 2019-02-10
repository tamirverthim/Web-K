package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Sequence;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public class MutationObserverInit {
    boolean childList = false;
    boolean attributes;
    boolean characterData;
    boolean subtree = false;
    boolean attributeOldValue;
    boolean characterDataOldValue;
    @DOMString Sequence<String> attributeFilter;
}
