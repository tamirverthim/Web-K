package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.Constructor;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.DefaultString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.NewObject;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Constructor
@Exposed(Window.class)
public interface Text extends CharacterData, Slotable {

    void constructor(@Optional @DefaultString("") @DOMString String data);

    @NewObject
    Text splitText(@Unsigned long offset);

    @ReadonlyAttribute
    @DOMString
    String wholeText();
}
