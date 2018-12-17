package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 10/30/2018
 */

public interface UIEvent extends Event {
    
    void constructor (String type, @Optional UIEventInit eventInitDict);
    
    @ReadonlyAttribute @Nullable Window view();
    @ReadonlyAttribute int detail();
}
