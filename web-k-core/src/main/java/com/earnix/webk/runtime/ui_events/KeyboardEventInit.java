package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public class KeyboardEventInit extends  EventModifierInit{
    @DOMString String key = "";
    @DOMString String code = "";
    @Unsigned int location = 0;
    boolean repeat = false;
    boolean isComposing = false;
}
