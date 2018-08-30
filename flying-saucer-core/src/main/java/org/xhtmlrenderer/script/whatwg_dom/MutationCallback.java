package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Callback;
import org.xhtmlrenderer.script.web_idl.Sequence;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface MutationCallback {
    void call(Sequence<MutationRecord> mutations, MutationObserver observer);
}
