package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Constructor;
import org.xhtmlrenderer.script.web_idl.Exposed;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Constructor
@Exposed(Window.class)
public interface DocumentFragment extends NonElementParentNode, ParentNode, Node{
}
