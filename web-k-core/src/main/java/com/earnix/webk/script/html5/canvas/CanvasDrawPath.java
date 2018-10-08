package com.earnix.webk.script.html5.canvas;

import com.earnix.webk.script.web_idl.DefaultString;
import com.earnix.webk.script.web_idl.Mixin;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasDrawPath {
    // path API (see also CanvasPath)
    void beginPath();

    void fill(@Optional @DefaultString("nonzero") CanvasFillRule fillRule);

//    void fill(Path2D path, @Optional @DefaultString("nonzero") CanvasFillRule fillRule);

    void stroke();

//    void stroke(Path2D path);

    void clip(@Optional @DefaultString("nonzero") CanvasFillRule fillRule);

    void clip(Path2D path, @Optional @DefaultString("nonzero") CanvasFillRule fillRule);

    void resetClip();

    boolean isPointInPath(@Unrestricted double x, @Unrestricted double y, @Optional @DefaultString("nonzero") CanvasFillRule fillRule);

    boolean isPointInPath(Path2D path, @Unrestricted double x, @Unrestricted double y, @Optional @DefaultString("nonzero") CanvasFillRule fillRule);

    boolean isPointInStroke(@Unrestricted double x, @Unrestricted double y);

    boolean isPointInStroke(Path2D path, @Unrestricted double x, @Unrestricted double y);
}
