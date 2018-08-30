package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.future.Worker;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface CanvasGradient {
    // opaque object
    void addColorStop(double offset, @DOMString String color);
}
