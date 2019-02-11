package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface TextMetrics {
    // x-direction
    @ReadonlyAttribute
    double width(); // advance width

    @ReadonlyAttribute
    double actualBoundingBoxLeft();

    @ReadonlyAttribute
    double actualBoundingBoxRight();

    // y-direction
    @ReadonlyAttribute
    double fontBoundingBoxAscent();

    @ReadonlyAttribute
    double fontBoundingBoxDescent();

    @ReadonlyAttribute
    double actualBoundingBoxAscent();

    @ReadonlyAttribute
    double actualBoundingBoxDescent();

    @ReadonlyAttribute
    double emHeightAscent();

    @ReadonlyAttribute
    double emHeightDescent();

    @ReadonlyAttribute
    double hangingBaseline();

    @ReadonlyAttribute
    double alphabeticBaseline();

    @ReadonlyAttribute
    double ideographicBaseline();
}
