package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.dom.EventTarget;

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
