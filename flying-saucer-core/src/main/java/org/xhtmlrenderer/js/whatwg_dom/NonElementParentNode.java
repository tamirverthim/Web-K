package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Mixin;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Mixin
public interface NonElementParentNode {
    @Optional Element getElementById(DOMString elementId);
}
