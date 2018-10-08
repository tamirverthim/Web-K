package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.NullTreat;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;
import com.earnix.kbrowser.script.web_idl.TreatNullAs;
import com.earnix.kbrowser.script.web_idl.Unsigned;

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

    @DOMString String substringData(@Unsigned int offset, @Unsigned int count);

    void appendData(@DOMString String data);

    void insertData(@Unsigned int offset, @DOMString String data);

    void deleteData(@Unsigned int offset, @Unsigned int count);

    void replaceData(@Unsigned int offset, @Unsigned int count, @DOMString String data);
}
