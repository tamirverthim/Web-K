package com.earnix.webk.script.html5.canvas;

import com.earnix.webk.script.web_idl.Mixin;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface CanvasImageData {
    // pixel manipulation
    ImageData createImageData(long sw, long sh);

    ImageData createImageData(ImageData imagedata);

    ImageData getImageData(long sx, long sy, long sw, long sh);

    void putImageData(ImageData imagedata, long dx, long dy);

    void putImageData(ImageData imagedata, long dx, long dy, long dirtyX, long dirtyY, long dirtyWidth, long dirtyHeight);
}
