package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.Dictionary;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
public class FocusEventInit extends UIEventInit{
    @Nullable EventTarget relatedTarget = null;
}
