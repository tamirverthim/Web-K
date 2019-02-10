package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.geom.DOMMatrix;
import com.earnix.webk.runtime.geom.DOMMatrix2DInit;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.NewObject;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.Unrestricted;

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
