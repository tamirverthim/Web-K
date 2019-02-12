package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.Required;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Dictionary
public class ShadowRootInit {
    @Required ShadowRootMode mode;
}
