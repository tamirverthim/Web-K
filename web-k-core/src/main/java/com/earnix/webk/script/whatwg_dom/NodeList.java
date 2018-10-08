package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Iterable;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface NodeList extends Iterable<Node> {

}
