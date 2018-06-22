package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface DOMImplementation {
    @NewObject
    DocumentType createDocumentType(DOMString qualifiedName, DOMString publicId, DOMString systemId);

    @NewObject
    XMLDocument createDocument(@Nullable DOMString namespace, @TreatNullAs(NullTreat.EmptyString) DOMString qualifiedName, @Optional @Nullable @DefaultNull DocumentType doctype);

    @NewObject
    Document createHTMLDocument(@Optional DOMString title);

    boolean hasFeature(); // useless; always returns true
}
