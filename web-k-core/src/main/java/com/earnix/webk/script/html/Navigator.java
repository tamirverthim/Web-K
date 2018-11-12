package com.earnix.webk.script.html;

import com.earnix.webk.script.web_idl.Exposed;

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
