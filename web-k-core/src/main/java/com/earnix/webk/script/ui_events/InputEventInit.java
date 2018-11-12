package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.DOMString;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public class InputEventInit extends UIEventInit {
    @DOMString String data = "";
    boolean isComposing = false;
}
