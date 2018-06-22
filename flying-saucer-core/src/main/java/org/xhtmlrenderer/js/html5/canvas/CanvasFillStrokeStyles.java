package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasFillStrokeStyles {
    
    // colors and styles (see also the CanvasPathDrawingStyles and CanvasTextDrawingStyles interfaces)
    @OneOf({DOMString.class, CanvasGradient.class, CanvasPattern.class})
    Attribute<Object> strokeStyle(); // (default black)

    @OneOf({DOMString.class, CanvasGradient.class, CanvasPattern.class})
    Attribute<Object> fillStyle(); // (default black)

    CanvasGradient createLinearGradient(double x0, double y0, double x1, double y1);

    CanvasGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1);

    @Optional
    CanvasPattern createPattern(CanvasImageSource image, @TreatNullAs(NullTreat.EmptyString) DOMString repetition);
}
