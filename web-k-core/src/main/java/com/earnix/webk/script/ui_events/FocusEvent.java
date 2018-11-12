package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public interface FocusEvent extends UIEvent {

    void constructor(@DOMString String type, @Optional FocusEventInit eventInitDict);

    @ReadonlyAttribute
    @Nullable
    EventTarget relatedTarget();

}
