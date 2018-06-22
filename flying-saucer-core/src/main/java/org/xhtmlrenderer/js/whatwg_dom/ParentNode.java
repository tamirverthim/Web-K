package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.*;

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
    void prepend(@OneOf({Node.class, DOMString.class}) Object... nodes);

    @CEReactions
    @Unscopable
    void append(@OneOf({Node.class, DOMString.class}) Object... nodes);

    @Optional
    Element querySelector(DOMString selectors);

    @NewObject
    NodeList querySelectorAll(DOMString selectors);
}
