package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public interface CompositionEvent {
    
    void constructor(@DOMString String type, @Optional CompositionEventInit eventInitDict);
    
    @ReadonlyAttribute @DOMString String data();
}
