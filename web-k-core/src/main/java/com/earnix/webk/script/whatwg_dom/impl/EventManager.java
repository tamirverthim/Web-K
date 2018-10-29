package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.Binder;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.whatwg_dom.Event;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.ArrayList;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventManager {
    
    ScriptContext scriptContext;
    DocumentModel document;
    
    public void publishEvent(ElementModel target, String type) {
        val chain = new ArrayList<ElementModel>();
        ElementModel current = target;
        do {
            chain.add(current);
            current = current.parent();
        } while (current != document);


        val jsEvent = new EventImpl(type, null);
        jsEvent.setPhase(Event.CAPTURING_PHASE);
        
        // capturing & target phases
        val iterator = chain.listIterator(chain.size());
        while (iterator.hasPrevious()) {
            val element = iterator.previous();
            val jsElement = Binder.getElement(element, scriptContext.getPanel());
            if(element == target) {
                jsEvent.setPhase(Event.AT_TARGET);
            }
            jsElement.dispatchEvent(jsEvent);
        }
        
        // bubbling
        for (ElementModel element : chain) {
            val jsElement = Binder.getElement(element, scriptContext.getPanel());
            
        }
    }
}
