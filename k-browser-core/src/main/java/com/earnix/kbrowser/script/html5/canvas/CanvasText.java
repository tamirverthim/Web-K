package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Mixin;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.Unrestricted;

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
