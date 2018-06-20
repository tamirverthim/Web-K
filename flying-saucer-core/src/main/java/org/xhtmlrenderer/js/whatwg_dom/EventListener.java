package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Callback;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Callback
public interface EventListener {
    void handleEvent(Event event);
}
