package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.*;

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
