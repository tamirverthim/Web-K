package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.geom.DOMMatrix;
import org.xhtmlrenderer.script.geom.DOMMatrix2DInit;
import org.xhtmlrenderer.script.web_idl.Mixin;
import org.xhtmlrenderer.script.web_idl.NewObject;
import org.xhtmlrenderer.script.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasTransform {
    // transformations (default transform is the identity matrix)
    void scale(@Unrestricted double x, @Unrestricted double y);

    void rotate(@Unrestricted double angle);

    void translate(@Unrestricted double x, @Unrestricted double y);

    void transform(@Unrestricted double a, @Unrestricted double b, @Unrestricted double c, @Unrestricted double d, @Unrestricted double e, @Unrestricted double f);

    @NewObject
    DOMMatrix getTransform();

    void setTransform(@Unrestricted double a, @Unrestricted double b, @Unrestricted double c, @Unrestricted double d, @Unrestricted double e, @Unrestricted double f);

    void setTransform(@Optional DOMMatrix2DInit transform);

    void resetTransform();
}
