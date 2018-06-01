package org.xhtmlrenderer.js.dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.NameList;
import org.xhtmlrenderer.js.web_idl.Attribute;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NameListImpl implements NameList {

    LinkedHashMap<DOMString, DOMString> data = new LinkedHashMap<>();
    ArrayList<DOMString> namesCache;
    private ArrayList<DOMString> urisCache;


    @Override
    public DOMString getName(long index) {
        return namesCache.get((int) index);
    }

    @Override
    public DOMString getNamespaceURI(long index) {
        return urisCache.get((int) index);
    }

    @Override
    public Attribute<Long> length() {
        return null;
    }

    @Override
    public boolean contains(DOMString str) {
        return namesCache.contains(str);
    }

    @Override
    public boolean containsNS(DOMString namespaceURI, DOMString name) {
        val uri = data.get(name);
        return uri != null && Objects.equals(uri, namespaceURI);
    }

    private void refresh() {
        namesCache = new ArrayList<>(data.keySet());
        urisCache = new ArrayList<>(data.values());
    }
}
