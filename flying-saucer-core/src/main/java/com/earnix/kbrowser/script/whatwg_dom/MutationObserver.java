package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Constructor;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.Sequence;

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
