package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.Uint8ClampedArray;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public abstract class ImageData {
    // readonly attribute unsigned 
    long width;
    // readonly attribute unsigned 
    long height;
    // readonly attribute 
    Uint8ClampedArray data;
}
