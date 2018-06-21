package org.xhtmlrenderer.js.html5.canvas;

/**
 * @author Taras Maslov
 * 6/21/2018
 */

import org.xhtmlrenderer.js.html5.HTMLVideoElement;
import org.xhtmlrenderer.js.html5.ImageBitmap;
import org.xhtmlrenderer.js.web_idl.Typedef;

@Typedef({HTMLOrSVGImageElement.class,
        HTMLVideoElement.class,
        HTMLCanvasElement.class,
        ImageBitmap.class,
        OffscreenCanvas.class})
public interface CanvasImageSource {
}
