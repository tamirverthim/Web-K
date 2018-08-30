package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Attribute;
import org.xhtmlrenderer.script.web_idl.Partial;
import org.xhtmlrenderer.script.web_idl.Readonly;
import org.xhtmlrenderer.script.web_idl.Replaceable;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
@Partial
public interface Window {
    @Replaceable @Readonly Attribute<?> event();
}
