package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Iterable;
import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface DOMTokenList extends Iterable<DOMString> {

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
