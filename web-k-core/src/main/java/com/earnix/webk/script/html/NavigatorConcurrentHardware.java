package com.earnix.webk.script.html;

import com.earnix.webk.script.web_idl.Mixin;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface NavigatorConcurrentHardware {
    @ReadonlyAttribute
    @Unsigned
    long hardwareConcurrency();
}
