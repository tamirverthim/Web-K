package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.DefaultBoolean;
import org.xhtmlrenderer.js.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/5/2018
 */
public interface CanvasPathMethods {
    // shared path API methods
    void closePath();

    void moveTo(@Unrestricted double x, @Unrestricted double y);

    void lineTo(@Unrestricted double x, @Unrestricted double y);

    void quadraticCurveTo(@Unrestricted double cpx, @Unrestricted double cpy, @Unrestricted double x, @Unrestricted double y);

    void bezierCurveTo(@Unrestricted double cp1x, @Unrestricted double cp1y, @Unrestricted double cp2x, @Unrestricted double cp2y, @Unrestricted double x, @Unrestricted double y);

    void arcTo(@Unrestricted double x1, @Unrestricted double y1, @Unrestricted double x2, @Unrestricted double y2, @Unrestricted double radius);

    void rect(@Unrestricted double x, @Unrestricted double y, @Unrestricted double w, @Unrestricted double h);

    void arc(@Unrestricted double x,
             @Unrestricted double y,
             @Unrestricted double radius,
             @Unrestricted double startAngle,
             @Unrestricted double endAngle,
             @Optional @DefaultBoolean(false) boolean counterclockwise
    );
}
