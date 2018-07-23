package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface NamedNodeMap extends LegacyUnenumerableNamedProperties {
    @Readonly
    @Unsigned
    Attribute<Long> length();

    @Getter
    @Nullable
    Attr item(@Unsigned long index);

    @Getter
    @Nullable
    Attr getNamedItem(DOMString qualifiedName);

    @Nullable
    Attr getNamedItemNS(@Nullable DOMString namespace, DOMString localName);

    @CEReactions
    @Nullable
    Attr setNamedItem(Attr attr);

    @CEReactions
    @Nullable
    Attr setNamedItemNS(Attr attr);

    @CEReactions
    Attr removeNamedItem(DOMString qualifiedName);

    @CEReactions
    Attr removeNamedItemNS(@Nullable DOMString namespace, DOMString localName);
}
