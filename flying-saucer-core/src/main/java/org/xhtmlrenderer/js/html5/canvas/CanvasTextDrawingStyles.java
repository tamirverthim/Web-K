package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.DefaultString;
import org.xhtmlrenderer.js.web_idl.Mixin;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface CanvasTextDrawingStyles {
    
    // text
    @DefaultString("10px sans-serif")
    Attribute<DOMString> font(); // (default 10px sans-serif)

    @DefaultString("start")
    Attribute<CanvasTextAlign> textAlign(); // (default: "start")

    @DefaultString("alphabetic")
    Attribute<CanvasTextBaseline> textBaseline(); // (default: "alphabetic")

    @DefaultString("inherit")
    Attribute<CanvasDirection> direction(); // (default: "inherit")
}
