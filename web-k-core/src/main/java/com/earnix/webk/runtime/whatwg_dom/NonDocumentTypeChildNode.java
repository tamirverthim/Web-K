package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public interface NonDocumentTypeChildNode {

    @Optional
    @ReadonlyAttribute
    Element previousElementSibling();

    @Optional
    @ReadonlyAttribute
    Element nextElementSibling();

}
