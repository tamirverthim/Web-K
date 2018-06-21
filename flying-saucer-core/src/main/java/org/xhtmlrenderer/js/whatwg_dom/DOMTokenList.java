package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface DOMTokenList extends Iterable<org.xhtmlrenderer.js.web_idl.DOMString> {
    @Readonly
    @Unsigned
    Attribute<Long> length();

    @Getter
    @Optional
    DOMString item(@Unsigned long index);

    boolean contains(DOMString token);

    @CEReactions
    void add(DOMString... tokens);

    @CEReactions
    void remove(DOMString... tokens);

    @CEReactions
    boolean toggle(DOMString token, @Optional Boolean force);

    @CEReactions
    boolean replace(DOMString token, DOMString newToken);

    boolean supports(DOMString token);

    @CEReactions
    @Stringifier
    Attribute<DOMString> value();
}
