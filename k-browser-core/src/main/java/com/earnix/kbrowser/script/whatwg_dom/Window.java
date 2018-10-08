package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.Partial;
import com.earnix.kbrowser.script.web_idl.Readonly;
import com.earnix.kbrowser.script.web_idl.Replaceable;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
@Partial
public interface Window {
    @Replaceable
    @Readonly
    Attribute<?> event();
}
