package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public interface InputEvent extends UIEvent {
    
    void constructor(@DOMString String type, @Optional KeyboardEventInit eventInitDict);
    
    @ReadonlyAttribute
    @DOMString String data();

    @ReadonlyAttribute
    boolean isComposing();
    
    
}
