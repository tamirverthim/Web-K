package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
public interface CanvasShadowStyles {
    // shadows
    @Unrestricted
    @DefaultDouble(0)
    Attribute<Double> shadowOffsetX(); // (default 0)

    @Unrestricted
    @DefaultDouble(0)
    Attribute<Double> shadowOffsetY(); // (default 0)

    @Unrestricted
    @DefaultDouble(0)
    Attribute<Double> shadowBlur(); // (default 0)

    @DefaultString("transparent black")
    @DOMString
    Attribute<String> shadowColor(); // (default transparent black)
}
