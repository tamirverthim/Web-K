package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.USVString;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Dictionary
public class WindowPostMessageOptions {
    @USVString String targetOrigin = "/";
}
