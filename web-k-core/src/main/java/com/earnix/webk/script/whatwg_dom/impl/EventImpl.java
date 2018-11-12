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
public class EventImpl implements Event {

    String type;
    boolean bubbles;
    boolean cancellable;
    boolean composed;
    
    @Getter @Setter short phase;
    @Getter boolean propagationStopped;
    @Getter @Setter EventTarget currentTarget;
    EventTarget target;
    @Setter Sequence<EventTarget> composedPath;
    @Getter @Setter boolean trusted;
    boolean defaultPrevented;
    
    Attribute<Boolean> cancelBubble = new Attribute<Boolean>() {
        @Override
        public Boolean get() {
            return propagationStopped;
        }

        @Override
        public void set(Boolean aBoolean) {
            EventImpl.this.propagationStopped = aBoolean;
        }
    };
    
    public EventImpl(String type, EventInit eventInit) {
        constructor(type, eventInit);
    }

    @Override
    public void constructor(@DOMString String type, EventInit init) {
        this.type = type;
        this.bubbles = init.bubbles;
        this.cancellable = init.cancelable;
        this.composed = init.composed;
    }

    @Override
    public @DOMString String type() {
        return type;
    }

    @Override
    public EventTarget target() {
        return target;
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
        propagationStopped = true;
    }

    @Override
    public Attribute<Boolean> cancelBubble() {
        return cancelBubble;
    }

    @Override
    public void stopImmediatePropagation() {

    }

    @Override
    public boolean bubbles() {
        return bubbles;
    }

    @Override
    public boolean cancelable() {
        return cancellable;
    }

    @Override
    public boolean returnValue() {
        return false;
    }

    @Override
    public void preventDefault() {
        defaultPrevented = true;
    }

    @Override
    public boolean defaultPrevented() {
        return defaultPrevented;
    }

    @Override
    public boolean composed() {
        return false;
    }

    @Override
    public boolean isTrusted() {
        return trusted;
    }

    @Override
    public DOMHighResTimeStamp timeStamp() {
        return null;
    }

    @Override
    public void initEvent(@DOMString String type, boolean bubbles, boolean cancelable) {

    }
}
