package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Exposed(Window.class)
public interface ShadowRoot extends DocumentOrShadowRoot {
    @ReadonlyAttribute
    ShadowRootMode mode();

    @ReadonlyAttribute
    Element host();
}
