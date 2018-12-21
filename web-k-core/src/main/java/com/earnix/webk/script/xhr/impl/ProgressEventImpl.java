package com.earnix.webk.script.xhr.impl;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.EventInit;
import com.earnix.webk.script.whatwg_dom.impl.EventImpl;
import com.earnix.webk.script.xhr.ProgressEvent;
import com.earnix.webk.script.xhr.ProgressEventInit;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 12/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgressEventImpl extends EventImpl implements ProgressEvent {

    @Setter
    int loaded;
    @Setter
    int total;

    public ProgressEventImpl(String type, EventInit eventInit) {
        super(type, eventInit);
    }

    @Override
    public void constructor(@DOMString String type, ProgressEventInit eventInitDict) {
        super.constructor(type, eventInitDict);
    }

    @Override
    public boolean lengthComputable() {
        return false;
    }

    @Override
    public int loaded() {
        return loaded;
    }

    @Override
    public int total() {
        return total;
    }
}
