package com.earnix.webk.script.html.canvas;

/**
 * @author Taras Maslov
 * 6/21/2018
 */

import com.earnix.webk.script.html.HTMLVideoElement;
import com.earnix.webk.script.html.ImageBitmap;
import com.earnix.webk.script.web_idl.Typedef;

@Typedef({HTMLOrSVGImageElement.class,
        HTMLVideoElement.class,
        HTMLCanvasElement.class,
        ImageBitmap.class,
        OffscreenCanvas.class})
public interface CanvasImageSource {
}
