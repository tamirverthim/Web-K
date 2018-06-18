package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/5/2018
 */
public interface CanvasDrawingStyles {
    // line caps/joins
    @Unrestricted Attribute<Double> lineWidth(); // (default: 1)
    @Unrestricted Attribute<DOMString> lineCap(); // "butt", "round", "square" (default: "butt")
    @Unrestricted Attribute<DOMString> lineJoin(); // "round", "bevel", "miter" (default: "miter")
    @Unrestricted Attribute<Double> miterLimit(); // (default: 10)
}
