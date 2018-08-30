package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Mixin;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Mixin
public interface NonElementParentNode {
    @Optional Element getElementById(@DOMString String elementId);
}
