package com.earnix.webk.runtime.xhr;

import com.earnix.webk.runtime.future.DedicatedWorker;
import com.earnix.webk.runtime.future.SharedWorker;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Exposed({Window.class, DedicatedWorker.class, SharedWorker.class})
public interface XMLHttpRequestUpload extends XMLHttpRequestEventTarget {
}
