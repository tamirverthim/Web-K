package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.Mixin;

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
