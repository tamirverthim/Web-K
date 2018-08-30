package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.script.web_idl.SameObject;

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
