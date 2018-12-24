package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Callback;
import com.earnix.webk.script.web_idl.impl.WebIDLAdapter;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Callback
public interface EventListener {
    void handleEvent(WebIDLAdapter<Event> event);
}
