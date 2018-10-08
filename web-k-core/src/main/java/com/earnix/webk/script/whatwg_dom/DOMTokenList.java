package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.CEReactions;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Iterable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.Stringifier;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface DOMTokenList extends Iterable<@DOMString String> {

    boolean contains(@DOMString String token);

    @CEReactions
    void add(@DOMString String... tokens);

    @CEReactions
    void remove(@DOMString String... tokens);

    @CEReactions
    boolean toggle(@DOMString String token, @Optional Boolean force);

    @CEReactions
    boolean replace(@DOMString String token, @DOMString String newToken);

    boolean supports(@DOMString String token);

    @CEReactions
    @Stringifier
    @DOMString
    Attribute<String> value();
}
