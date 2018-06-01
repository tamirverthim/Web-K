package org.xhtmlrenderer.js.canvas.impl;

import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.canvas.BlobCallback;
import org.xhtmlrenderer.js.canvas.HTMLCanvasElement;
import org.xhtmlrenderer.js.canvas.RenderingContext;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class HTMLCanvasElementImpl implements HTMLCanvasElement {
    @Override
    public RenderingContext getContext(DOMString contextId, Object... arguments) {
        return null;
    }

    @Override
    public boolean probablySupportsContext(DOMString contextId, Object... arguments) {
        return false;
    }

    @Override
    public DOMString toDataURL(DOMString type, Object... arguments) {
        return null;
    }

    @Override
    public void toBlob(BlobCallback _callback, DOMString type, Object... arguments) {

    }
}
