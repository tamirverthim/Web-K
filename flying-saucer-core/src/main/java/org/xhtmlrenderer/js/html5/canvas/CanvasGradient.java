package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.future.Worker;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasGradient {
    // opaque object
    void addColorStop(double offset, DOMString color);
}
