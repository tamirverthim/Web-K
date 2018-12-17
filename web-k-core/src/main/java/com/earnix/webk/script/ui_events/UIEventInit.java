package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.Dictionary;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.whatwg_dom.EventInit;
import com.earnix.webk.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
public class UIEventInit extends EventInit {
    public @Nullable Window view = null;
    public int detail = 0;
}
