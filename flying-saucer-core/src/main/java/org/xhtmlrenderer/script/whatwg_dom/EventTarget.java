package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.OneOf;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
public interface EventTarget {
    
    void addEventListener(
            @DOMString String type, 
            @Optional EventListener callback, 
            @Optional @OneOf({AddEventListenerOptions.class, Boolean.class}) Object options
    );
    
    void removeEventListener(
            @DOMString String type, 
            @Optional EventListener callback, 
            @Optional @OneOf({EventListenerOptions.class, Boolean.class}) Object options
    );
  
    boolean dispatchEvent(Event event);
}
