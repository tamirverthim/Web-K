package com.earnix.webk.script.html;

import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Exposed(Window.class)
public interface BarProp {
    @ReadonlyAttribute
    boolean visible();
}
