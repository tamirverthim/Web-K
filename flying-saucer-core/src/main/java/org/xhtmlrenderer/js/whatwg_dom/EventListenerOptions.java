package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.DefaultBoolean;
import org.xhtmlrenderer.js.web_idl.Dictionary;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Dictionary
public interface EventListenerOptions {
    @DefaultBoolean(false) boolean capture();
    
}
