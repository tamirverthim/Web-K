package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.DefaultDouble;
import com.earnix.kbrowser.script.web_idl.DefaultString;
import com.earnix.kbrowser.script.web_idl.Unrestricted;

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
