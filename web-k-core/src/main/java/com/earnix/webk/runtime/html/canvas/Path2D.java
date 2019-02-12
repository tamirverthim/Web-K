package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.OneOf;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.dom.Window;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface Path2D extends CanvasPath {
    void constructor(@Optional @DOMString(oneOfIndex = 1) @OneOf({Path2D.class, String.class}) Object path);
}
