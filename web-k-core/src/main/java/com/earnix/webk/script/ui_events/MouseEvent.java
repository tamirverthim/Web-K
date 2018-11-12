package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.Readonly;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Unsigned;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventTarget;

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
