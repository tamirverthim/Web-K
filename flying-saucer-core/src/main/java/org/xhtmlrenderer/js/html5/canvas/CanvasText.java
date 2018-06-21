package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Mixin;
import org.xhtmlrenderer.js.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface CanvasText {
    // text (see also the CanvasPathDrawingStyles and CanvasTextDrawingStyles interfaces)
    void fillText(DOMString text, @Unrestricted double x, @Unrestricted double y, @Optional @Unrestricted double maxWidth);

    void strokeText(DOMString text, @Unrestricted double x, @Unrestricted double y, @Optional @Unrestricted double maxWidth);

    TextMetrics measureText(DOMString text);
}
