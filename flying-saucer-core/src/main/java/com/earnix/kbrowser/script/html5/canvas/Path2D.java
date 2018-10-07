package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.OneOf;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface Path2D extends CanvasPath {
    void construct(@Optional @DOMString(oneOfIndex = 1) @OneOf({Path2D.class, String.class}) Object path);
}
