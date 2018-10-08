package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.SameObject;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface MutationRecord {
    @ReadonlyAttribute
    @DOMString String type();

    @SameObject
    @ReadonlyAttribute
    Node target();

    @SameObject
    @ReadonlyAttribute
    NodeList addedNodes();

    @SameObject
    @ReadonlyAttribute
    NodeList removedNodes();

    @Optional
    @ReadonlyAttribute
    Node previousSibling();

    @Optional
    @ReadonlyAttribute
    Node nextSibling();

    @Optional
    @ReadonlyAttribute
    @DOMString String attributeName();

    @Optional
    @ReadonlyAttribute
    @DOMString String attributeNamespace();

    @Optional
    @ReadonlyAttribute
    @DOMString String oldValue();
}
