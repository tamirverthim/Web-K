package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class})
public interface AbstractRange {
    @ReadonlyAttribute
    Node startContainer();

    @Unsigned
    @ReadonlyAttribute
    long startOffset();

    @ReadonlyAttribute
    Node endContainer();

    @Unsigned
    @ReadonlyAttribute
    long endOffset();

    @ReadonlyAttribute
    boolean collapsed();
}
