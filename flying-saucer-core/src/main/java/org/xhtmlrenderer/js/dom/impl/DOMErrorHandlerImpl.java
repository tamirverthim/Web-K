package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.DOMError;
import org.xhtmlrenderer.js.dom.DOMErrorHandler;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class DOMErrorHandlerImpl implements DOMErrorHandler {
    @Override
    public boolean handleError(DOMError error) {
        return false;
    }
}
