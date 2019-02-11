package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.Constructor;
import com.earnix.webk.runtime.web_idl.Exposed;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Constructor
@Exposed(Window.class)
public interface DocumentFragment extends NonElementParentNode, ParentNode, Node {
}
