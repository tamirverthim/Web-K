package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.OneOf;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
public interface EventTarget {
    
    void addEventListener(
            DOMString type, 
            @Optional EventListener callback, 
            @Optional @OneOf({AddEventListenerOptions.class, Boolean.class}) Object options
    );
    
    void removeEventListener(
            DOMString type, 
            @Optional EventListener callback, 
            @Optional @OneOf({EventListenerOptions.class, Boolean.class}) Object options
    );
  
    boolean dispatchEvent(Event event);
}
