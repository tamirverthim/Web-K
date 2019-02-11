package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Unsigned;

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
