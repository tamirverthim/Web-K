package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.DefaultBoolean;
import org.xhtmlrenderer.script.web_idl.Dictionary;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Dictionary
public interface AddEventListenerOptions extends EventListenerOptions{

    @DefaultBoolean(false) boolean passive();
    @DefaultBoolean(false) boolean once();
}
