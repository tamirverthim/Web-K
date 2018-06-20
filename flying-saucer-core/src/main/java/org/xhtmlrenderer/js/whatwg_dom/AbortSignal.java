package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.future.Worker;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Exposed({Window.class, Worker.class})
public interface AbortSignal extends EventTarget{
    @Readonly
    Attribute<Boolean> aborted();

    Attribute<EventHandler> onabort();
}
