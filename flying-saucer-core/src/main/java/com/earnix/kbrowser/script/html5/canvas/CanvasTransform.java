package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.geom.DOMMatrix;
import com.earnix.kbrowser.script.geom.DOMMatrix2DInit;
import com.earnix.kbrowser.script.web_idl.Mixin;
import com.earnix.kbrowser.script.web_idl.NewObject;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.Unrestricted;

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
