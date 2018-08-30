package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface ProcessingInstruction {
    @ReadonlyAttribute @DOMString String target();
}
