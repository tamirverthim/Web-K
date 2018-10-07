package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Dictionary;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
@Dictionary
public interface EventInit {
    boolean bubbles = false;
    boolean cancelable = false;
    boolean composed = false;
}
