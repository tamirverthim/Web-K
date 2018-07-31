package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface Attr extends Node {
    @ReadonlyAttribute
    @Nullable
    DOMString namespaceURI();

    @ReadonlyAttribute
    @Nullable
    DOMString prefix();

    @ReadonlyAttribute
    DOMString localName();

    @ReadonlyAttribute
    DOMString name();

    @CEReactions
    Attribute<DOMString> value();

    @ReadonlyAttribute
    @Nullable
    Element ownerElement();

    @ReadonlyAttribute
    boolean specified(); // useless; always returns true
}
