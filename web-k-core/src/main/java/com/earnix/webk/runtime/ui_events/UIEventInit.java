package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.whatwg_dom.EventInit;
import com.earnix.webk.runtime.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
public class UIEventInit extends EventInit {
    public @Nullable Window view = null;
    public int detail = 0;
}
