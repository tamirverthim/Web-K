package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Dictionary;
import lombok.AllArgsConstructor;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
@Dictionary
public class EventInit {
    public boolean bubbles = false;
    public boolean cancelable = false;
    public boolean composed = false;
}
