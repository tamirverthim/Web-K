package com.earnix.webk.script.html;

import com.earnix.webk.script.web_idl.Mixin;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface NavigatorCookies {
    @ReadonlyAttribute boolean cookieEnabled();
}
