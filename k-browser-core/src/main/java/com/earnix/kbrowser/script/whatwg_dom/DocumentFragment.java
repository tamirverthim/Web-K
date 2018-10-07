package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Constructor;
import com.earnix.kbrowser.script.web_idl.Exposed;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Constructor
@Exposed(Window.class)
public interface DocumentFragment extends NonElementParentNode, ParentNode, Node {
}
