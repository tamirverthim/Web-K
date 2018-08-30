package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Iterable;
import org.xhtmlrenderer.script.web_idl.Exposed;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface NodeList extends Iterable<Node> {

}
