package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.Constructor;
import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.web_idl.Sequence;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
@Constructor
public interface MutationObserver {
    void construct(MutationCallback callback);

    void observe(Node target, @Optional MutationObserverInit options);
    
    void disconnect();
    
    Sequence<MutationRecord> takeRecords();
}
