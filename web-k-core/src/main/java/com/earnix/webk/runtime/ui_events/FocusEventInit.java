package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
public class FocusEventInit extends UIEventInit{
    @Nullable EventTarget relatedTarget = null;
}
