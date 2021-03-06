package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.CEReactions;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.OneOf;
import com.earnix.webk.runtime.web_idl.Unscopable;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface ChildNode {
    @CEReactions
    @Unscopable
    void before(@DOMString(oneOfIndex = 1) @OneOf({Node.class, String.class}) Object... nodes);

    @CEReactions
    @Unscopable
    void after(@DOMString(oneOfIndex = 1) @OneOf({Node.class, String.class}) Object... nodes);

    @CEReactions
    @Unscopable
    void replaceWith(@DOMString(oneOfIndex = 1) @OneOf({Node.class, String.class}) Object... nodes);

    @CEReactions
    @Unscopable
    void remove();
}
