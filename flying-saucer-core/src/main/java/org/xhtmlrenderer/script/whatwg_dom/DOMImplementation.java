package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface DOMImplementation {
    
    @NewObject
    DocumentType createDocumentType(@DOMString String qualifiedName, @DOMString String publicId, @DOMString String systemId);

    @NewObject
    XMLDocument createDocument(@Nullable @DOMString String namespace, @TreatNullAs(NullTreat.EmptyString) @DOMString String qualifiedName, @Optional @Nullable @DefaultNull DocumentType doctype);

    @NewObject
    Document createHTMLDocument(@Optional @DOMString String title);

    boolean hasFeature(); // useless; always returns true
    
}
