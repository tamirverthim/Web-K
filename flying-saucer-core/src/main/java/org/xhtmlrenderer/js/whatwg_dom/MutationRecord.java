package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.js.web_idl.SameObject;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface MutationRecord {
    @ReadonlyAttribute
    DOMString type();

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
    DOMString attributeName();

    @Optional
    @ReadonlyAttribute
    DOMString attributeNamespace();

    @Optional
    @ReadonlyAttribute
    DOMString oldValue();
}
