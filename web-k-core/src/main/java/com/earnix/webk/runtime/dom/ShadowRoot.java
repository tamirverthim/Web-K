package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

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
