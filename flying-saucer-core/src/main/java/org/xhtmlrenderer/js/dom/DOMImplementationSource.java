package org.xhtmlrenderer.js.dom;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface DOMImplementationSource {
    DOMImplementation  getDOMImplementation(DOMString features);
    DOMImplementationList getDOMImplementationList(DOMString features);
}
