package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.DOMString;
import org.xhtmlrenderer.js.Nullable;
import org.xhtmlrenderer.js.Optional;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface HTMLCanvasElement {
    //attribute unsigned 
    long width = 0;
    //attribute unsigned 
    long height = 0;

    @Nullable RenderingContext getContext(DOMString contextId, Object... arguments);
    boolean probablySupportsContext(DOMString contextId, Object... arguments);

    DOMString toDataURL(@Optional DOMString type, Object... arguments);
    
    void toBlob(BlobCallback _callback, @Optional DOMString type, Object... arguments);
}
