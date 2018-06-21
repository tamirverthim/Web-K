package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Callback;
import org.xhtmlrenderer.js.web_idl.Sequence;
import org.xhtmlrenderer.js.whatwg_dom.MutationObserver;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface MutationCallback {
    void call(Sequence<MutationRecord> mutations, MutationObserver observer);
}
