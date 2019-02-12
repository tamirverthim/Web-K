package com.earnix.webk.runtime.xhr;

import com.earnix.webk.runtime.future.DedicatedWorker;
import com.earnix.webk.runtime.future.SharedWorker;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.dom.EventHandler;
import com.earnix.webk.runtime.dom.EventTarget;
import com.earnix.webk.runtime.dom.Window;

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
