package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Sequence;

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
