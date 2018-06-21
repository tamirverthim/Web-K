package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.web_idl.OneOf;
import org.xhtmlrenderer.js.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
public interface Path2D extends CanvasPath {
    void construct(@Optional @OneOf({Path2D.class, DOMString.class}) Object path);
}
