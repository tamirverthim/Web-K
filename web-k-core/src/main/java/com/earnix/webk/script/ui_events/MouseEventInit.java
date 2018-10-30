package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.Dictionary;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Unsigned;
import com.earnix.webk.script.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
public class MouseEventInit extends UIEventInit {
    int screenX = 0;
    int screenY = 0;
    int clientX = 0;
    int clientY = 0;

    short button = 0;
    @Unsigned short buttons = 0;
    @Nullable EventTarget relatedTarget = null;
}
