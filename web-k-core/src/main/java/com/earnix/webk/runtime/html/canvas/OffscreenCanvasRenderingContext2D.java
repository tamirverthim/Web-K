package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.future.Worker;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.whatwg_dom.Window;

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
        CanvasPath {
    void commit();

    @ReadonlyAttribute
    OffscreenCanvas canvas();
}
