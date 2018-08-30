package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public interface NonDocumentTypeChildNode {
    
    @Optional @ReadonlyAttribute Element previousElementSibling();
    
    @Optional @ReadonlyAttribute Element nextElementSibling();
    
}
