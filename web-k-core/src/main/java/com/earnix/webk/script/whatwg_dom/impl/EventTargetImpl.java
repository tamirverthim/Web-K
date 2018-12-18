package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventInit;
import com.earnix.webk.script.whatwg_dom.EventListener;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Slf4j
public class EventTargetImpl implements EventTarget {

    private LinkedHashMap<String, List<EventListener>> listeners = new LinkedHashMap<>();
    
    @Override
    public void addEventListener(@DOMString String type, EventListener callback, Object options) {
        var typeListeners = listeners.computeIfAbsent(type, k -> new ArrayList<>());
        typeListeners.add(callback);

        // workaround for ChartJS
        if (type.equals("animationstart")) {
            dispatchEvent(new EventImpl("animationstart", new EventInit()));
        }
    }

    @Override
    public void removeEventListener(@DOMString String type, EventListener callback, Object options) {
        val typeListeners = listeners.get(type);
        if (typeListeners != null) {
            typeListeners.remove(callback);
            if (typeListeners.isEmpty()) {
                listeners.remove(type);
            }
        }
    }

    @Override
    public boolean dispatchEvent(Event event) {
//        log.trace("Dispatched event {} on target {}", event.type(), toString());
        val typeListeners = listeners.get(event.type());
        if (typeListeners != null) {
            typeListeners.forEach(l -> l.handleEvent(event));
            return true;
        }
        return false;
    }
}
