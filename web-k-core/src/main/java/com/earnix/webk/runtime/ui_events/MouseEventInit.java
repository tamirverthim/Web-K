package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Unsigned;
import com.earnix.webk.runtime.dom.EventTarget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
@FieldDefaults(level = AccessLevel.PUBLIC)
public class MouseEventInit extends UIEventInit {
    int screenX = 0;
    int screenY = 0;
    int clientX = 0;
    int clientY = 0;

    short button = 0;
    @Unsigned short buttons = 0;
    @Nullable EventTarget relatedTarget = null;
}
