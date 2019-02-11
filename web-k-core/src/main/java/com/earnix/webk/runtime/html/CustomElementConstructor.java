package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Callback;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Callback
public interface CustomElementConstructor {
    Object call();
}
