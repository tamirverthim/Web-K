package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.future.Worker;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.Constructor;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Readonly;
import com.earnix.webk.script.web_idl.SameObject;

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
