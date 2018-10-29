package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Sequence;
import com.earnix.webk.script.whatwg_dom.DOMHighResTimeStamp;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventInit;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 8/14/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class EventImpl implements Event {

    String type;
    short phase;

    public EventImpl(String type, EventInit eventInit) {
        construct(type, eventInit);
    }

    @Override
    public void construct(@DOMString String type, EventInit init) {
        this.type = type;
    }

    @Override
    public @DOMString String type() {
        return type;
    }

    @Override
    public EventTarget target() {
        return null;
    }

    @Override
    public EventTarget srcElement() {
        return null;
    }

    @Override
    public EventTarget currentTarget() {
        return null;
    }

    @Override
    public Sequence<EventTarget> composedPath() {
        return null;
    }

    @Override
    public Short eventPhase() {
        return null;
    }

    @Override
    public void stopPropagation() {
    }

    @Override
    public Attribute<Boolean> cancelBubble() {
        return null;
    }

    @Override
    public void stopImmediatePropagation() {

    }

    @Override
    public boolean bubbles() {
        return false;
    }

    @Override
    public boolean cancelable() {
        return false;
    }

    @Override
    public boolean returnValue() {
        return false;
    }

    @Override
    public void preventDefault() {

    }

    @Override
    public boolean defaultPrevented() {
        return false;
    }

    @Override
    public boolean composed() {
        return false;
    }

    @Override
    public boolean isTrusted() {
        return false;
    }

    @Override
    public DOMHighResTimeStamp timeStamp() {
        return null;
    }

    @Override
    public void initEvent(@DOMString String type, boolean bubbles, boolean cancelable) {

    }
}
