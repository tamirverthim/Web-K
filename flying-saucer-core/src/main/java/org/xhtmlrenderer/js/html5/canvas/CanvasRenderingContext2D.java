package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.js.whatwg_dom.Window;

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
        RenderingContext
{
    // back-reference to the canvas
    @ReadonlyAttribute HTMLCanvasElement canvas();
}
