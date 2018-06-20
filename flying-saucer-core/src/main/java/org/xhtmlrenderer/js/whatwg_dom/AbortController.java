package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.future.Worker;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Exposed({Window.class, Worker.class})
@Constructor
public interface AbortController {
    @SameObject @Readonly Attribute<AbortSignal> signal();

    void abort();
}
