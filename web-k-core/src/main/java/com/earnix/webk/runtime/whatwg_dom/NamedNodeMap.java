package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.CEReactions;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Getter;
import com.earnix.webk.runtime.web_idl.LegacyUnenumerableNamedProperties;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface NamedNodeMap extends LegacyUnenumerableNamedProperties {

    @ReadonlyAttribute
    @Unsigned
    int length();

    Object item(int index);

    @Getter
    @Nullable
    Attr getNamedItem(@DOMString String qualifiedName);

    @Nullable
    Attr getNamedItemNS(@Nullable @DOMString String namespace, @DOMString String localName);

    @CEReactions
    @Nullable
    Attr setNamedItem(Attr attr);

    @CEReactions
    @Nullable
    Attr setNamedItemNS(Attr attr);

    @CEReactions
    Attr removeNamedItem(@DOMString String qualifiedName);

    @CEReactions
    Attr removeNamedItemNS(@Nullable @DOMString String namespace, @DOMString String localName);
}
