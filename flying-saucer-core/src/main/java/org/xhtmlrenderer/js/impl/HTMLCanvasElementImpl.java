package org.xhtmlrenderer.js.impl;

import org.jsoup.nodes.Element;
import org.xhtmlrenderer.js.html5.canvas.BlobCallback;
import org.xhtmlrenderer.js.html5.canvas.HTMLCanvasElement;
import org.xhtmlrenderer.js.html5.canvas.OffscreenCanvas;
import org.xhtmlrenderer.js.html5.canvas.RenderingContext;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import sun.awt.image.ToolkitImage;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public class HTMLCanvasElementImpl extends ElementImpl implements HTMLCanvasElement {


    private final CanvasRenderingContext2DImpl context;

    public HTMLCanvasElementImpl(Element target, int width, int heigth) {
        super(target);
        context = new CanvasRenderingContext2DImpl(width, heigth);
    }

    @Override
    public Attribute<Long> width() {
        return Attribute.<Long>readOnly().give(() -> Long.valueOf(context.getWidth()));
    }

    @Override
    public Attribute<Long> height() {
        return Attribute.<Long>readOnly().give(() -> Long.valueOf(context.getHeight()));
    }

    @Override
    public RenderingContext getContext(DOMString contextId, Object options) {
        return context;
    }

    @Override
    public USVString toDataURL(DOMString type, Object quality) {
        return null;
    }

    @Override
    public void toBlob(BlobCallback _callback, DOMString type, Object quality) {

    }

    @Override
    public OffscreenCanvas transferControlToOffscreen() {
        return null;
    }

    public CanvasRenderingContext2DImpl getContextImpl() {
        return context;
    }
}
