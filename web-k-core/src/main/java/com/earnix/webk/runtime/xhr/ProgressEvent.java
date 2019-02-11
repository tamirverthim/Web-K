package com.earnix.webk.runtime.xhr;

import com.earnix.webk.runtime.future.DedicatedWorker;
import com.earnix.webk.runtime.future.SharedWorker;
import com.earnix.webk.runtime.web_idl.Constructor;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Unsigned;
import com.earnix.webk.runtime.whatwg_dom.Event;
import com.earnix.webk.runtime.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Constructor
@Exposed({Window.class, DedicatedWorker.class, SharedWorker.class})
public interface ProgressEvent extends Event {

    void constructor(@DOMString String type, @Optional ProgressEventInit eventInitDict);

    @ReadonlyAttribute
    boolean lengthComputable();

    @ReadonlyAttribute
    @Unsigned
    int loaded();

    @ReadonlyAttribute
    @Unsigned
    int total();
}
