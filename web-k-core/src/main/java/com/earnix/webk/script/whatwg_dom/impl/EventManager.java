package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.impl.SequenceImpl;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.EventInit;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.swing.SwingUtilities;
import java.util.ArrayList;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class EventManager {

    ScriptContext scriptContext;

    public void publishEvent(ElementModel elementModel, EventImpl event) {
        publishEvent(ScriptDOMFactory.getElement(scriptContext, elementModel), event);
    }

    public void publishEvent(Element target, EventImpl event) {
        
        log.debug("Dispatching event {} to {}", event, target);

        scriptContext.storeDocumentHash();

        // preparing propagation path 
        val propagationPath = new ArrayList<EventTarget>();
        Element current = target;
        do {
            propagationPath.add(current);
            current = current.parentElement();
        } while (current != null);

        event.setTarget(target);
        
//        event.setTrusted(true);
        event.setPhase(Event.CAPTURING_PHASE);
        event.setComposedPath(new SequenceImpl<>(propagationPath));

        // capturing & target phases
        val iterator = propagationPath.listIterator(propagationPath.size());
        while (iterator.hasPrevious()) {
            val element = iterator.previous();
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

        if (event.bubbles() && !event.isPropagationStopped()) {
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
        
        scriptContext.handleDocumentHashUpdate();
    }

    public void publishEvent(EventTarget target, EventImpl event) {
        event.setTarget(target);
        target.dispatchEvent(event);
    }

    /**
     * https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-eventgroupings-htmlevents-h3
     */
    public void onchange(ElementModel target) {
        EventInit init = new EventInit();
        init.bubbles = true;
        EventImpl event = new EventImpl("change", init);
        publishEvent(target, event);
    }
}
