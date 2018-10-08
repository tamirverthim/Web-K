package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Callback;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Callback
public interface EventListener {
    void handleEvent(Event event);
}
