package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface CharacterData extends Node {

    // raises(DOMException) on setting
    // raises(DOMException) on retrieval
    Attribute<DOMString> data() throws DOMException;

    @Readonly
    @Unsigned
    Attribute<Long> length();

    DOMString substringData(@Unsigned long offset, @Unsigned long count) throws DOMException;

    void appendData(DOMString arg) throws DOMException;

    void insertData(@Unsigned long offset, DOMString arg) throws DOMException;

    void deleteData(@Unsigned long offset, @Unsigned long count) throws DOMException;

    void replaceData(@Unsigned long offset, @Unsigned long count, DOMString arg) throws DOMException;
}
