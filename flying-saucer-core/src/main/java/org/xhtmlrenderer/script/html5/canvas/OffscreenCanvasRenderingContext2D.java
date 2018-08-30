package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.future.Worker;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed({Window.class, Worker.class})
public interface OffscreenCanvasRenderingContext2D extends
        CanvasState,
        CanvasTransform,
        CanvasCompositing,
        CanvasImageSmoothing,
        CanvasFillStrokeStyles,
        CanvasShadowStyles,
        CanvasFilters,
        CanvasRect,
        CanvasDrawPath,
        CanvasDrawImage,
        CanvasImageData,
        CanvasPathDrawingStyles,
        CanvasPath{
    void commit();
    @ReadonlyAttribute OffscreenCanvas canvas();
}
