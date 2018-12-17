package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.EventListener;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.var;

import java.util.HashMap;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Level1EventTarget {

    EventTarget eventTarget;
    HashMap<String, EventHandlerAttribute> handlers = new HashMap<>();

    public Level1EventTarget(EventTarget eventTarget) {
        this.eventTarget = eventTarget;

    }

    public Attribute<EventHandler> getHandlerAttribute(String eventType) {
        var handler = handlers.get(eventType);
        if (handler == null) {
            handler = new EventHandlerAttribute();
            handlers.put(eventType, handler);
            eventTarget.addEventListener(eventType, handler, null);
        }
        return handler;
    }

    private static class EventHandlerAttribute implements Attribute<EventHandler>, EventListener {
        EventHandler eventHandler;

        @Override
        public EventHandler get() {
            return eventHandler;
        }

        @Override
        public void set(EventHandler eventHandler) {
            this.eventHandler = eventHandler;
        }

        @Override
        public void handleEvent(Event event) {
            if (eventHandler != null) {
                eventHandler.call(event);
            }
        }
    }
}
