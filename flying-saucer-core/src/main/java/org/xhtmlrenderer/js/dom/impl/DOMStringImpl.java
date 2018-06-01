package org.xhtmlrenderer.js.dom.impl;

import lombok.AllArgsConstructor;
import org.xhtmlrenderer.js.dom.DOMString;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
@AllArgsConstructor
public class DOMStringImpl implements DOMString {
    private String value;
    @Override
    public String toString() {
        return value;
    }
}
