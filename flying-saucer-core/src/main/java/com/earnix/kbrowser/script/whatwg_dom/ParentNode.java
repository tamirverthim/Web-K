package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.CEReactions;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Mixin;
import com.earnix.kbrowser.script.web_idl.NewObject;
import com.earnix.kbrowser.script.web_idl.OneOf;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.Readonly;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;
import com.earnix.kbrowser.script.web_idl.SameObject;
import com.earnix.kbrowser.script.web_idl.Unscopable;
import com.earnix.kbrowser.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Mixin
public interface ParentNode {

    @SameObject
    @ReadonlyAttribute
    HTMLCollection children();

    @Readonly
    @Optional
    @ReadonlyAttribute
    Element firstElementChild();

    @Optional
    @ReadonlyAttribute
    Element lastElementChild();

    @Unsigned
    @ReadonlyAttribute
    Integer childElementCount();

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
