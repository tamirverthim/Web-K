package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.future.Worker;
import com.earnix.kbrowser.script.geom.DOMMatrix2DInit;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasPattern {
    // opaque object
    void setTransform(@Optional DOMMatrix2DInit transform);
}
