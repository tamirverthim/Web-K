package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Partial;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Replaceable;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
@Partial
public interface Window {
    @Replaceable @Readonly Attribute<?> event();
}
