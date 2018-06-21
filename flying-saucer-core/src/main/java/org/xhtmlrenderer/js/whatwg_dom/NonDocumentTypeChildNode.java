package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public interface NonDocumentTypeChildNode {
    @Optional @ReadonlyAttribute Element previousElementSibling();
    @Optional @ReadonlyAttribute Element nextElementSibling();
}
