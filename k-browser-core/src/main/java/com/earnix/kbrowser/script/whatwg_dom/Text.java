package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Constructor;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.DefaultString;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.NewObject;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;
import com.earnix.kbrowser.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Constructor(true)
@Exposed(Window.class)
public interface Text extends CharacterData, Slotable {

    void construct(@Optional @DefaultString("") @DOMString String data);

    @NewObject
    Text splitText(@Unsigned long offset);

    @ReadonlyAttribute
    @DOMString
    String wholeText();
}
