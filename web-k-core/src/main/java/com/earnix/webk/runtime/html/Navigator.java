package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Exposed;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Exposed(Window.class)
public interface Navigator extends 
        NavigatorID, 
        NavigatorLanguage, 
        NavigatorOnLine, 
        NavigatorContentUtils, 
        NavigatorCookies, 
        NavigatorPlugins, 
        NavigatorConcurrentHardware {
}
