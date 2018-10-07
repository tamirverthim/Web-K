package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;

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
