package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Mixin
public interface ParentNode {
    
    @SameObject
    @ReadonlyAttribute HTMLCollection children();

    @Readonly
    @Optional
    @ReadonlyAttribute Element firstElementChild();
    
    @Optional
    @ReadonlyAttribute Element lastElementChild();
    
    @Unsigned
    @ReadonlyAttribute Integer childElementCount();

    @CEReactions
    @Unscopable
    void prepend(@DOMString(oneOfIndex = 1) @OneOf({Node.class, String.class}) Object... nodes);

    @CEReactions
    @Unscopable
    void append(@DOMString(oneOfIndex = 2) @OneOf({Node.class, String.class}) Object... nodes);

    @Optional
    Element querySelector(@DOMString String selectors);

    @NewObject
    NodeList querySelectorAll(@DOMString String selectors);
    
}
