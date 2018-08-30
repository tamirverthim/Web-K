package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.future.Worker;
import org.xhtmlrenderer.script.web_idl.*;

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
