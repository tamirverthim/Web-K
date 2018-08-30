package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.future.Worker;
import org.xhtmlrenderer.script.geom.DOMMatrix2DInit;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasPattern {
    // opaque object
    void setTransform(@Optional DOMMatrix2DInit transform);
}
