package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Constructor;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Optional;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Constructor(true)
@Exposed(Window.class)
public interface Comment extends CharacterData {
    void construct(@Optional @DOMString String data);
}
