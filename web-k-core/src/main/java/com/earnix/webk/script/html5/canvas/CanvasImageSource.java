package com.earnix.webk.script.html5.canvas;

/**
 * @author Taras Maslov
 * 6/21/2018
 */

import com.earnix.webk.script.html5.HTMLVideoElement;
import com.earnix.webk.script.html5.ImageBitmap;
import com.earnix.webk.script.web_idl.Typedef;

@Typedef({HTMLOrSVGImageElement.class,
        HTMLVideoElement.class,
        HTMLCanvasElement.class,
        ImageBitmap.class,
        OffscreenCanvas.class})
public interface CanvasImageSource {
}
