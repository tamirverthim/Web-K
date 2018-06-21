package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface CharacterData extends NonDocumentTypeChildNode, ChildNode, Node {
    @TreatNullAs(NullTreat.EmptyString)
    Attribute<DOMString> data();

    @ReadonlyAttribute
    @Unsigned
    long length();

    DOMString substringData(@Unsigned long offset, @Unsigned long count);

    void appendData(DOMString data);

    void insertData(@Unsigned long offset, DOMString data);

    void deleteData(@Unsigned long offset, @Unsigned long count);

    void replaceData(@Unsigned long offset, @Unsigned long count, DOMString data);
}
