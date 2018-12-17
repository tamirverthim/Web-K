package com.earnix.webk.script.ui_events.impl;

import com.earnix.webk.script.ui_events.MouseEvent;
import com.earnix.webk.script.ui_events.MouseEventInit;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public class MouseEventImpl extends UIEventImpl implements MouseEvent {

    public MouseEventImpl(String type, MouseEventInit eventInit){
        super(type, eventInit);
    }

    @Override
    public int screenX() {
        return 0;
    }

    @Override
    public int screenY() {
        return 0;
    }

    @Override
    public int clientX() {
        return 0;
    }

    @Override
    public int clientY() {
        return 0;
    }

    @Override
    public boolean ctrlKey() {
        return false;
    }

    @Override
    public boolean shiftKey() {
        return false;
    }

    @Override
    public boolean altKey() {
        return false;
    }

    @Override
    public boolean metaKey() {
        return false;
    }

    @Override
    public short button() {
        return 0;
    }

    @Override
    public short buttons() {
        return 0;
    }

    @Override
    public EventTarget relatedTarget() {
        return null;
    }

    @Override
    public boolean getModifierState(@DOMString String keyArg) {
        return false;
    }

    @Override
    public void constructor(@DOMString String type, MouseEventInit eventInitDict) {
        super.constructor(type, eventInitDict);
    }
}
