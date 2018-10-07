package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.future.Worker;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasGradient {
    // opaque object
    void addColorStop(double offset, @DOMString String color);
}
