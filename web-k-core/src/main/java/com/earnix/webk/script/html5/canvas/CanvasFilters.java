package com.earnix.webk.script.html5.canvas;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DefaultString;
import com.earnix.webk.script.web_idl.Mixin;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasFilters {
    // filters
    @DefaultString("none")
    Attribute<String> filter(); // (default "none")
}
