package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.*;

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
    Attribute<String> globalCompositeOperation(); // (default source-over)
}
