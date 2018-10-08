package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.future.Worker;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Exposed({Window.class, Worker.class})
public interface AbortSignal extends EventTarget {
    @Readonly
    Attribute<Boolean> aborted();

    Attribute<EventHandler> onabort();
}
