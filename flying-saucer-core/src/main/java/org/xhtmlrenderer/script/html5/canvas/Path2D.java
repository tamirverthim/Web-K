package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.OneOf;
import org.xhtmlrenderer.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface Path2D extends CanvasPath {
    void construct(@Optional @DOMString(oneOfIndex = 1) @OneOf({Path2D.class, String.class}) Object path);
}
