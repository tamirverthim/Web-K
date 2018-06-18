package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.DOMImplementation;
import org.xhtmlrenderer.js.dom.DOMImplementationList;
import org.xhtmlrenderer.js.dom.DOMImplementationSource;
import org.xhtmlrenderer.js.web_idl.DOMString;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class DOMImplementationSourceImpl implements DOMImplementationSource {
    @Override
    public DOMImplementation getDOMImplementation(DOMString features) {
        return null;
    }

    @Override
    public DOMImplementationList getDOMImplementationList(DOMString features) {
        return null;
    }
}
