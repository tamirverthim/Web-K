package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.whatwg_dom.Window;
import com.earnix.webk.script.whatwg_dom.impl.EventImpl;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
public class UIEventImpl extends EventImpl implements UIEvent {
    
    public UIEventImpl(String type, UIEventInit init) {
        super(type, init);
    }
    
    @Override
    public void constructor(String type, UIEventInit eventInitDict) {
        super.construct(type, eventInitDict);
    }

    @Override
    public Window view() {
        return null;
    }

    @Override
    public int detail() {
        return 0;
    }
}
