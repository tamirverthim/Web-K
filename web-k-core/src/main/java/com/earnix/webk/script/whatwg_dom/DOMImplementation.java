package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.DefaultNull;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.NewObject;
import com.earnix.webk.script.web_idl.NullTreat;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.TreatNullAs;

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
