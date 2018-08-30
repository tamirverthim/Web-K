package org.xhtmlrenderer.script.html5.canvas;

/**
 * @author Taras Maslov
 * 6/21/2018
 */

import org.xhtmlrenderer.script.html5.HTMLVideoElement;
import org.xhtmlrenderer.script.html5.ImageBitmap;
import org.xhtmlrenderer.script.web_idl.Typedef;

@Typedef({HTMLOrSVGImageElement.class,
        HTMLVideoElement.class,
        HTMLCanvasElement.class,
        ImageBitmap.class,
        OffscreenCanvas.class})
public interface CanvasImageSource {
}
