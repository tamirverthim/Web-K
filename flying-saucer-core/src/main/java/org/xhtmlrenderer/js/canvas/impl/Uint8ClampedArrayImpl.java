package org.xhtmlrenderer.js.canvas.impl;

import lombok.Getter;
import org.xhtmlrenderer.js.Uint8ClampedArray;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
public class Uint8ClampedArrayImpl implements Uint8ClampedArray {
    
    @Getter
    byte[] bytes;

    public Uint8ClampedArrayImpl(byte[] data) {
        
    }
}
