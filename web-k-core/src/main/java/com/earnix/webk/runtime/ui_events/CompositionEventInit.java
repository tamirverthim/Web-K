package com.earnix.webk.runtime.ui_events;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Dictionary;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
public class CompositionEventInit extends UIEventInit {
    @DOMString String data = "";
}
