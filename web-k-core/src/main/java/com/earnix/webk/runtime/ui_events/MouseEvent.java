package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Unsigned;
import com.earnix.webk.runtime.whatwg_dom.Event;
import com.earnix.webk.runtime.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public interface MouseEvent extends Event {

    @ReadonlyAttribute
    int screenX();

    @ReadonlyAttribute
    int screenY();

    @ReadonlyAttribute
    int clientX();

    @ReadonlyAttribute
    int clientY();

    @ReadonlyAttribute
    boolean ctrlKey();

    @ReadonlyAttribute
    boolean shiftKey();

    @ReadonlyAttribute
    boolean altKey();

    @ReadonlyAttribute
    boolean metaKey();

    @ReadonlyAttribute
    short button();

    @ReadonlyAttribute
    @Unsigned
    short buttons();

    @ReadonlyAttribute
    @Nullable
    EventTarget relatedTarget();

    boolean getModifierState(@DOMString String keyArg);

    void constructor(@DOMString String type, @Optional MouseEventInit eventInitDict);
}
