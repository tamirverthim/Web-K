package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.SameObject;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface NavigatorPlugins {
    @SameObject
    @ReadonlyAttribute
    PluginArray plugins();

    @SameObject
    @ReadonlyAttribute
    MimeTypeArray mimeTypes();

    boolean javaEnabled();

}
