package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.dom.DOMString;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface CanvasGradient {
    void addColorStop(double offset, DOMString color);
}
