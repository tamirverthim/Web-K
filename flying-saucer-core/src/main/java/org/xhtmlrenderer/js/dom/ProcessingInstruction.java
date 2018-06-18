package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 6/4/2018
 */
public interface ProcessingInstruction {
    @Readonly
    Attribute<DOMString> target();

    Attribute<DOMString> data();
    // raises(DOMException) on setting
}
