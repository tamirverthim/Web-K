package com.earnix.webk.runtime.ui_events.impl;

import com.earnix.webk.runtime.ui_events.MouseEvent;
import com.earnix.webk.runtime.ui_events.MouseEventInit;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.whatwg_dom.EventTarget;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MouseEventImpl extends UIEventImpl implements MouseEvent {

    @Setter
    boolean alt;

    @Setter
    boolean shift;

    @Setter
    boolean ctrl;

    @Setter
    boolean meta;

    int screenX;
    int screenY;
    int clientX;
    int clientY;
    short button;
    short buttons;

    EventTarget relatedTarget;

    public MouseEventImpl(String type, MouseEventInit eventInit){
        super(type, eventInit);
        constructor(type, eventInit);
    }

    @Override
    public int screenX() {
        return screenX;
    }

    @Override
    public int screenY() {
        return screenY;
    }

    @Override
    public int clientX() {
        return clientX;
    }

    @Override
    public int clientY() {
        return clientY;
    }

    @Override
    public boolean ctrlKey() {
        return ctrl;
    }

    @Override
    public boolean shiftKey() {
        return shift;
    }

    @Override
    public boolean altKey() {
        return alt;
    }

    @Override
    public boolean metaKey() {
        return meta;
    }

    @Override
    public short button() {
        return button;
    }

    @Override
    public short buttons() {
        return buttons;
    }

    @Override
    public EventTarget relatedTarget() {
        return relatedTarget;
    }

    @Override
    public boolean getModifierState(@DOMString String keyArg) {
        return false;
    }

    @Override
    public void constructor(@DOMString String type, MouseEventInit eventInit) {
        super.constructor(type, eventInit);
        clientX = eventInit.clientX;
        clientY = eventInit.clientY;
        screenX = eventInit.screenX;
        screenY = eventInit.screenY;
        button = eventInit.button;
        buttons = eventInit.buttons;
        relatedTarget = eventInit.relatedTarget;
    }
}
