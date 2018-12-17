package com.earnix.webk.script.ui_events.impl;

import com.earnix.webk.script.ui_events.UIEvent;
import com.earnix.webk.script.ui_events.UIEventInit;
import com.earnix.webk.script.whatwg_dom.Window;
import com.earnix.webk.script.whatwg_dom.impl.EventImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UIEventImpl extends EventImpl implements UIEvent {

    Window view;
    int detail;
    
    public UIEventImpl(String type, UIEventInit init) {
        super(type, init);
    }
    
    @Override
    public void constructor(String type, UIEventInit eventInitDict) {
        super.constructor(type, eventInitDict);
        this.view = eventInitDict.view;
        this.detail = eventInitDict.detail;
    }

    @Override
    public Window view() {
        return view;
    }

    @Override
    public int detail() {
        return detail;
    }
}
