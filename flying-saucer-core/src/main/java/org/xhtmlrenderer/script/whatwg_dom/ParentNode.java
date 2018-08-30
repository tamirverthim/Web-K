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
    @Readonly
    Attribute<HTMLCollection> children();

    @Readonly
    @Optional
    Attribute<Element> firstElementChild();

    @Readonly
    @Optional
    Attribute<Element> lastElementChild();

    @Readonly
    @Unsigned
    Attribute<Long> childElementCount();

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
