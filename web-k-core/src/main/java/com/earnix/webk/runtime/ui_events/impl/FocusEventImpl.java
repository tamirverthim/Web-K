package com.earnix.webk.runtime.ui_events.impl;

import com.earnix.webk.runtime.ui_events.FocusEvent;
import com.earnix.webk.runtime.ui_events.FocusEventInit;
import com.earnix.webk.runtime.ui_events.UIEventInit;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.whatwg_dom.EventTarget;
import lombok.Setter;

/**
 * @author Taras Maslov
 * 12/17/2018
 */
public class FocusEventImpl extends UIEventImpl implements FocusEvent {

    @Setter
    private EventTarget relatedTarget;

    public FocusEventImpl(String type, UIEventInit init) {
        super(type, init);
    }

    @Override
    public void constructor(@DOMString String type, FocusEventInit eventInitDict) {
        super.constructor(type, eventInitDict);
    }

    @Override
    public EventTarget relatedTarget() {
        return relatedTarget;
    }


}
