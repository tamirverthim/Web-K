package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface Attr extends Node {

    @ReadonlyAttribute
    @Nullable
    @DOMString String namespaceURI();

    @ReadonlyAttribute
    @Nullable
    @DOMString String prefix();

    @ReadonlyAttribute
    @DOMString String localName();

    @ReadonlyAttribute
    @DOMString String name();

    @CEReactions
    @DOMString
    Attribute<String> value();

    @ReadonlyAttribute
    @Nullable
    Element ownerElement();

    @ReadonlyAttribute
    boolean specified(); // useless; always returns true

}
