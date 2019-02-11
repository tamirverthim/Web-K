package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.USVString;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface NavigatorContentUtils {
    void registerProtocolHandler(@DOMString String scheme, @USVString String url, @DOMString String title);

    void unregisterProtocolHandler(@DOMString String scheme, @USVString String url);
}
