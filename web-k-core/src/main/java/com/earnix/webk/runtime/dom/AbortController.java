package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.future.Worker;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Constructor;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Readonly;
import com.earnix.webk.runtime.web_idl.SameObject;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Exposed({Window.class, Worker.class})
@Constructor
public interface AbortController {
    @SameObject
    @Readonly
    Attribute<AbortSignal> signal();

    void abort();
}
