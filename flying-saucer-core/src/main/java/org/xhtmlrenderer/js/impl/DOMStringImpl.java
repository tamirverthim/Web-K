package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.js.web_idl.DOMString;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DOMStringImpl implements DOMString {
    String value;

    public DOMStringImpl(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
