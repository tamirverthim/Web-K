package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.future.Worker;
import org.xhtmlrenderer.js.geom.DOMMatrix2DInit;
import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasPattern {
    // opaque object
    void setTransform(@Optional DOMMatrix2DInit transform);
}
