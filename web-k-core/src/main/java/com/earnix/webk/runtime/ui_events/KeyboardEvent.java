package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public interface KeyboardEvent extends UIEvent {
    // KeyLocationCode
    @Unsigned int DOM_KEY_LOCATION_STANDARD = 0x00;
    @Unsigned int DOM_KEY_LOCATION_LEFT = 0x01;
    @Unsigned int DOM_KEY_LOCATION_RIGHT = 0x02;
    @Unsigned int DOM_KEY_LOCATION_NUMPAD = 0x03;

    // region partial interface KeyboardEvent
    
    @ReadonlyAttribute @Unsigned int charCode();
    
    @ReadonlyAttribute @Unsigned int keyCode();
    
    @ReadonlyAttribute @Unsigned int which();
    
    // endregion
    
    void constructor(@DOMString String type, @Optional KeyboardEventInit eventInitDict);
    
    @ReadonlyAttribute
    @DOMString String key();

    @ReadonlyAttribute
    @DOMString String code();

    @ReadonlyAttribute
    @Unsigned
    int location();

    @ReadonlyAttribute
    boolean ctrlKey();

    @ReadonlyAttribute
    boolean shiftKey();

    @ReadonlyAttribute
    boolean altKey();

    @ReadonlyAttribute
    boolean metaKey();

    @ReadonlyAttribute
    boolean repeat();

    @ReadonlyAttribute
    boolean isComposing();

    boolean getModifierState(@DOMString String keyArg);
}
