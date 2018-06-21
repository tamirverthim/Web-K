package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasCompositing {
    // compositing
    @Unrestricted
    @DefaultDouble(1.0)
    Attribute<Double> globalAlpha(); // (default 1.0)
    
    @DefaultString("source-over")
    Attribute<DOMString> globalCompositeOperation(); // (default source-over)
}
