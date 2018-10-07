package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.DefaultBoolean;
import com.earnix.kbrowser.script.web_idl.Dictionary;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Dictionary
public interface AddEventListenerOptions extends EventListenerOptions {

    @DefaultBoolean(false)
    boolean passive();

    @DefaultBoolean(false)
    boolean once();
}
