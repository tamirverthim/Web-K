package com.earnix.kbrowser.script.html5.canvas;

/**
 * @author Taras Maslov
 * 6/21/2018
 */

import com.earnix.kbrowser.script.html5.HTMLVideoElement;
import com.earnix.kbrowser.script.html5.ImageBitmap;
import com.earnix.kbrowser.script.web_idl.Typedef;

@Typedef({HTMLOrSVGImageElement.class,
        HTMLVideoElement.class,
        HTMLCanvasElement.class,
        ImageBitmap.class,
        OffscreenCanvas.class})
public interface CanvasImageSource {
}
