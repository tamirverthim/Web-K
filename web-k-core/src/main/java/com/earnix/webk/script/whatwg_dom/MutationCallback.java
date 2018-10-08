package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Callback;
import com.earnix.webk.script.web_idl.Sequence;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface MutationCallback {
    void call(Sequence<MutationRecord> mutations, MutationObserver observer);
}
