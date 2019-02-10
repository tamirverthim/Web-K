package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.Sequence;
import com.earnix.webk.runtime.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface CanvasPathDrawingStyles {
    // line caps/joins
    @Unrestricted
    Attribute<Double> lineWidth(); // (default 1)

    Attribute<CanvasLineCap> lineCap(); // (default "butt")

    Attribute<CanvasLineJoin> lineJoin(); // (default "miter")

    @Unrestricted
    Attribute<Double> miterLimit(); // (default 10)

    // dashed lines
    void setLineDash(Sequence<Double> segments); // default empty // generic is unrestricted, todo handle

    Sequence<Double> getLineDash();

    @Unrestricted
    Attribute<Double> lineDashOffset();
}
