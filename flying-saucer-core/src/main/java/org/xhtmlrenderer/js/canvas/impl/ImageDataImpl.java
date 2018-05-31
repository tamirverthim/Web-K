package org.xhtmlrenderer.js.canvas.impl;

import lombok.AllArgsConstructor;
import org.xhtmlrenderer.js.Nullable;
import org.xhtmlrenderer.js.Uint8ClampedArray;
import org.xhtmlrenderer.js.canvas.ImageData;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@AllArgsConstructor
public class ImageDataImpl extends ImageData {
    public ImageDataImpl(double w, double h, @Nullable byte[] data){
        this.width = (long) w;
        this.height = (long) w;
        this.data = new Uint8ClampedArrayImpl(data);
    }
}
