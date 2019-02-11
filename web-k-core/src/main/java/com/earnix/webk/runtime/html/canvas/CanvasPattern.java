package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.future.Worker;
import com.earnix.webk.runtime.geom.DOMMatrix2DInit;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasPattern {
    // opaque object
    void setTransform(@Optional DOMMatrix2DInit transform);
}
