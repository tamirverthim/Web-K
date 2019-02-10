package com.earnix.webk.runtime.html.canvas;

/**
 * @author Taras Maslov
 * 6/21/2018
 */

import com.earnix.webk.runtime.html.HTMLVideoElement;
import com.earnix.webk.runtime.html.ImageBitmap;
import com.earnix.webk.runtime.web_idl.Typedef;

@Typedef({HTMLOrSVGImageElement.class,
        HTMLVideoElement.class,
        HTMLCanvasElement.class,
        ImageBitmap.class,
        OffscreenCanvas.class})
public interface CanvasImageSource {
}
