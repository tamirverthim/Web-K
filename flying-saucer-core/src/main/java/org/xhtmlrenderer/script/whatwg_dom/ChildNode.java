package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.*;

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
