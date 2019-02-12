package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.Constructor;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Optional;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Constructor
@Exposed(Window.class)
public interface Comment extends CharacterData {
    void constructor(@Optional @DOMString String data);
}
