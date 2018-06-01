package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface Text extends CharacterData {
    Text splitText(@Unsigned long offset) throws DOMException;

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<Boolean> isElementContentWhitespace();

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<DOMString> wholeText();

    // Introduced in DOM Level 3:
    Text replaceWholeText(DOMString content) throws DOMException;
}
