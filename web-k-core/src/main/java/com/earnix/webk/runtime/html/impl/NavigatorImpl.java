package com.earnix.webk.runtime.html.impl;

import com.earnix.webk.runtime.html.MimeTypeArray;
import com.earnix.webk.runtime.html.Navigator;
import com.earnix.webk.runtime.html.PluginArray;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.FrozenArray;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
public class NavigatorImpl implements Navigator {
    
    @Override
    public long hardwareConcurrency() {
        return 0;
    }

    @Override
    public void registerProtocolHandler(@DOMString String scheme, String url, @DOMString String title) {

    }

    @Override
    public void unregisterProtocolHandler(@DOMString String scheme, String url) {

    }

    @Override
    public boolean cookieEnabled() {
        return false;
    }

    @Override
    public @DOMString String appCodeName() {
        return null;
    }

    @Override
    public @DOMString String appName() {
        return null;
    }

    @Override
    public @DOMString String appVersion() {
        return null;
    }

    @Override
    public @DOMString String platform() {
        return null;
    }

    @Override
    public @DOMString String product() {
        return null;
    }

    @Override
    public @DOMString String productSub() {
        return null;
    }

    @Override
    public @DOMString String userAgent() {
        return null;
    }

    @Override
    public @DOMString String vendor() {
        return null;
    }

    @Override
    public @DOMString String vendorSub() {
        return null;
    }

    @Override
    public @DOMString String language() {
        return null;
    }

    @Override
    public @DOMString FrozenArray<String> languages() {
        return null;
    }

    @Override
    public boolean onLine() {
        return false;
    }

    @Override
    public PluginArray plugins() {
        return null;
    }

    @Override
    public MimeTypeArray mimeTypes() {
        return null;
    }

    @Override
    public boolean javaEnabled() {
        return false;
    }
}
