package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface CharacterData extends NonDocumentTypeChildNode, ChildNode, Node {
    
    @TreatNullAs(NullTreat.EmptyString)
    @DOMString
    Attribute<String> data();

    @ReadonlyAttribute
    @Unsigned
    long length();

    @DOMString  String substringData(@Unsigned int offset, @Unsigned int count);

    void appendData(@DOMString String data);

    void insertData(@Unsigned int offset, @DOMString String data);

    void deleteData(@Unsigned int offset, @Unsigned int count);

    void replaceData(@Unsigned int offset, @Unsigned int count, @DOMString String data);
}
