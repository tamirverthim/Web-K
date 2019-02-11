package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface NavigatorCookies {
    @ReadonlyAttribute boolean cookieEnabled();
}
