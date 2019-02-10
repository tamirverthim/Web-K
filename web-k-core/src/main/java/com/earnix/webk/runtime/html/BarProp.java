package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Exposed(Window.class)
public interface BarProp {
    @ReadonlyAttribute
    boolean visible();
}
