package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;

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
