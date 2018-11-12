package com.earnix.webk.script.xhr;

import com.earnix.webk.script.future.DedicatedWorker;
import com.earnix.webk.script.future.SharedWorker;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import com.earnix.webk.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Exposed({Window.class, DedicatedWorker.class, SharedWorker.class})
public interface XMLHttpRequestEventTarget extends EventTarget {
    // event handlers
    Attribute<EventHandler> onloadstart();

    Attribute<EventHandler> onprogress();

    Attribute<EventHandler> onabort();

    Attribute<EventHandler> onerror();

    Attribute<EventHandler> onload();

    Attribute<EventHandler> ontimeout();

    Attribute<EventHandler> onloadend();
    
}
