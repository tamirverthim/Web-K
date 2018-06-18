package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/4/2018
 */
public interface HTMLCanvasElement {
    
    @Unsigned
    Attribute<Long> width();

    @Unsigned
    Attribute<Long> height();

    DOMString toDataURL(@Optional DOMString type, Object... args);

    CanvasRenderingContext2D getContext(DOMString contextId);
}
