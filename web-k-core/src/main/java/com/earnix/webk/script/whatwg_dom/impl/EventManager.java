package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.Binder;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.impl.SequenceImpl;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.ArrayList;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EventManager {

    ScriptContext scriptContext;

    public void publishEvent(EventTarget target, Event event){
        
    };
    
    public void publishEvent(ElementModel targetModel, EventImpl event) {
        val target = Binder.getElement(targetModel, scriptContext);

        // preparing propagation path 
        val propagationPath = new ArrayList<EventTarget>();
        Element current = target;
        do {
            propagationPath.add(current);
            current = current.parentElement();
        } while (current != Binder.get(scriptContext.getPanel().getDocument(), scriptContext));
        
        
        event.setTrusted(true);
        event.setPhase(Event.CAPTURING_PHASE);
        event.setComposedPath(new SequenceImpl<>(propagationPath));

        // capturing & target phases
        val iterator = propagationPath.listIterator(propagationPath.size());
        while (iterator.hasPrevious()) {
            val element = iterator.previous();
//            val jsElement = Binder.getElement(element, scriptContext.getPanel());
            if (element == target) {
                event.setPhase(Event.AT_TARGET);
            }
            event.setCurrentTarget(element);
            element.dispatchEvent(event);

            // cancel if propagation stopped
            if (event.isPropagationStopped()) {
                break;
            }
        }

        if (!event.isPropagationStopped()) {
            // bubbling
            event.setPhase(Event.BUBBLING_PHASE);
            for (EventTarget eventTarget : propagationPath) {
                if (eventTarget == target) {
                    continue;
                }
                event.setCurrentTarget(eventTarget);
                eventTarget.dispatchEvent(event);
            }
        }
        scriptContext.getPanel().reset();
    }
}
