package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;
import com.earnix.kbrowser.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface CanvasRenderingContext2D extends
        CanvasState,
        CanvasTransform,
        CanvasCompositing,
        CanvasImageSmoothing,
        CanvasFillStrokeStyles,
        CanvasShadowStyles,
        CanvasFilters,
        CanvasRect,
        CanvasDrawPath,
        CanvasUserInterface,
        CanvasText,
        CanvasDrawImage,
        CanvasImageData,
        CanvasPathDrawingStyles,
        CanvasTextDrawingStyles,
        CanvasPath,
        //  typedef
        RenderingContext {
    // back-reference to the canvas
    @ReadonlyAttribute
    HTMLCanvasElement canvas();
}
