package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Constructor;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.Sequence;

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
