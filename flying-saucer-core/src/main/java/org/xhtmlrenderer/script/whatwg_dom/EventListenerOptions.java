package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.DefaultBoolean;
import org.xhtmlrenderer.script.web_idl.Dictionary;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Dictionary
public interface EventListenerOptions {
    @DefaultBoolean(false) boolean capture();
    
}
