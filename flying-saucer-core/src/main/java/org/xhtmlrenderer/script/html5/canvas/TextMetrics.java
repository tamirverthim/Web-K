package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.script.whatwg_dom.Window;

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