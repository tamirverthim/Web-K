package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.OneOf;
import com.earnix.webk.script.web_idl.Optional;

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
