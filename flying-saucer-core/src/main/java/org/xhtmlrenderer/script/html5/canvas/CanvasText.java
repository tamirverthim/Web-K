package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Mixin;
import org.xhtmlrenderer.script.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface CanvasText {

    // text (see also the CanvasPathDrawingStyles and CanvasTextDrawingStyles interfaces)
    void fillText(@DOMString String text, @Unrestricted double x, @Unrestricted double y, @Optional @Unrestricted Double maxWidth);

    void strokeText(@DOMString String text, @Unrestricted double x, @Unrestricted double y, @Optional @Unrestricted Double maxWidth);

    TextMetrics measureText(@DOMString String text);

}
