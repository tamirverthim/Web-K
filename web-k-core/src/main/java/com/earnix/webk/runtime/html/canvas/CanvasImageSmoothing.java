package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Mixin;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasImageSmoothing {
    // image smoothing
    Attribute<Boolean> imageSmoothingEnabled(); // (default true)

    Attribute<ImageSmoothingQuality> imageSmoothingQuality(); // (default low)
}
