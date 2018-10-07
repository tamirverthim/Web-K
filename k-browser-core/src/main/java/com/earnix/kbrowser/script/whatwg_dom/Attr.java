package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.CEReactions;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Nullable;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;

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
