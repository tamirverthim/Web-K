package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.CharacterData;
import org.xhtmlrenderer.js.dom.DOMException;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class CharacterDataImpl extends NodeImpl implements CharacterData {
    
    public CharacterDataImpl(org.w3c.dom.CharacterData characterData) {
        super(null);
    }

    @Override
    public Attribute<DOMString> data() throws DOMException {
        return null;
    }

    @Override
    public Attribute<Long> length() {
        return null;
    }

    @Override
    public DOMString substringData(long offset, long count) throws DOMException {
        return null;
    }

    @Override
    public void appendData(DOMString arg) throws DOMException {

    }

    @Override
    public void insertData(long offset, DOMString arg) throws DOMException {

    }

    @Override
    public void deleteData(long offset, long count) throws DOMException {

    }

    @Override
    public void replaceData(long offset, long count, DOMString arg) throws DOMException {

    }
}
