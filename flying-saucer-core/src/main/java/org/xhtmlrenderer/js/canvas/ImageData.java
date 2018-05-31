package org.xhtmlrenderer.js.canvas;

import lombok.Getter;
import org.xhtmlrenderer.js.Uint8ClampedArray;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
@Getter
public abstract class ImageData {
    @Attribute(readonly = true, unsigned =true)
    protected long width;
    
    @Attribute(readonly = true, unsigned = true) 
    protected long height;
    
    @Attribute(readonly = true) 
    protected Uint8ClampedArray data;
}
