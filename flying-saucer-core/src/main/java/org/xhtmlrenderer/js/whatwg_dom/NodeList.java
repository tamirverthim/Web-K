package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Iterable;
import org.xhtmlrenderer.js.web_idl.Exposed;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface NodeList extends Iterable<Node> {

}
