package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface ChildNode {
    @CEReactions
    @Unscopable
    void before(@OneOf({Node.class, DOMString.class}) Object... nodes);
    
    @CEReactions
    @Unscopable
    void after(@OneOf({Node.class, DOMString.class}) Object... nodes);

    @CEReactions
    @Unscopable
    void replaceWith(@OneOf({Node.class, DOMString.class}) Object... nodes);

    @CEReactions
    @Unscopable
    void remove();
}
