package com.earnix.webk.script.cssom;

import com.earnix.webk.script.web_idl.NewObject;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.Partial;
import com.earnix.webk.script.whatwg_dom.Element;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Partial
public interface Window {
    
    @NewObject
    CSSStyleDeclaration getComputedStyle(Element elt, @Optional @CSSOMString @Nullable String pseudoElt);
}
