package org.xhtmlrenderer.js.canvas.impl;

import lombok.AllArgsConstructor;
import org.xhtmlrenderer.js.Nullable;
import org.xhtmlrenderer.js.Uint8ClampedArray;
import org.xhtmlrenderer.js.canvas.ImageData;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
public class ImageDataImpl implements ImageData {

    double w,h;
    Uint8ClampedArrayImpl array;

    public ImageDataImpl(double w, double h, Uint8ClampedArrayImpl array) {
        this.w = w;
        this.h = h;
        this.array = array;
    }

    @Override
    public Attribute<Long> width() {
        return null;
    }

    @Override
    public Attribute<Long> height() {
        return null;
    }

    @Override
    public Attribute<Uint8ClampedArray> data() {
        return null;
    }
}
