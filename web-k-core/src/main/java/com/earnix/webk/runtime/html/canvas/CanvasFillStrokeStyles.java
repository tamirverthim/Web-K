package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.NullTreat;
import com.earnix.webk.runtime.web_idl.OneOf;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.TreatNullAs;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasFillStrokeStyles {

    // colors and styles (see also the CanvasPathDrawingStyles and CanvasTextDrawingStyles interfaces)
    @DOMString(oneOfIndex = 0)
    @OneOf({String.class, CanvasGradient.class, CanvasPattern.class})
    Attribute<Object> strokeStyle(); // (default black)

    @DOMString(oneOfIndex = 0)
    @OneOf({String.class, CanvasGradient.class, CanvasPattern.class})
    Attribute<Object> fillStyle(); // (default black)

    CanvasGradient createLinearGradient(double x0, double y0, double x1, double y1);

    CanvasGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1);

    @Optional
    CanvasPattern createPattern(CanvasImageSource image, @DOMString @TreatNullAs(NullTreat.EmptyString) String repetition);
}
