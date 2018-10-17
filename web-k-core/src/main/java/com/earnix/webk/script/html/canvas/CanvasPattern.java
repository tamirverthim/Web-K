package com.earnix.webk.script.html.canvas;

import com.earnix.webk.script.future.Worker;
import com.earnix.webk.script.geom.DOMMatrix2DInit;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasPattern {
    // opaque object
    void setTransform(@Optional DOMMatrix2DInit transform);
}
