package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.impl.WebIDLAdapter;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.EventListener;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.var;

import java.util.HashMap;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Level1EventTarget {

    final ScriptContext scriptContext;
    final EventTarget eventTarget;
    
    HashMap<String, EventHandlerAttribute> handlers = new HashMap<>();

    public Attribute<EventHandler> getHandlerAttribute(String eventType) {
        var handler = handlers.get(eventType);
        if (handler == null) {
            handler = new EventHandlerAttribute();
            handlers.put(eventType, handler);
            eventTarget.addEventListener(eventType.substring(2), handler, null);
        }
        return handler;
    }

    private class EventHandlerAttribute implements Attribute<EventHandler>, EventListener {
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
        public void handleEvent(WebIDLAdapter<Event> event) {
            if (eventHandler != null) {
                eventHandler.call(event);
            }
        }
    }
}
