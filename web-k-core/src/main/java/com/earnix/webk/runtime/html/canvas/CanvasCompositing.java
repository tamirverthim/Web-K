package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.DefaultDouble;
import com.earnix.webk.runtime.web_idl.DefaultString;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.Unrestricted;

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
