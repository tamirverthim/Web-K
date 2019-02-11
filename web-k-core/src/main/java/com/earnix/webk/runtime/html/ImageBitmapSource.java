package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.future.Blob;
import com.earnix.webk.runtime.html.canvas.CanvasImageSource;
import com.earnix.webk.runtime.html.canvas.ImageData;
import com.earnix.webk.runtime.web_idl.Typedef;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Typedef({CanvasImageSource.class, Blob.class, ImageData.class})
public interface ImageBitmapSource {
}
