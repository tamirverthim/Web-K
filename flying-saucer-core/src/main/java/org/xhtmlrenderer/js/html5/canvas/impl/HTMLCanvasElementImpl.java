package org.xhtmlrenderer.js.html5.canvas.impl;

import org.jsoup.nodes.Element;
import org.xhtmlrenderer.js.html5.canvas.BlobCallback;
import org.xhtmlrenderer.js.html5.canvas.HTMLCanvasElement;
import org.xhtmlrenderer.js.html5.canvas.OffscreenCanvas;
import org.xhtmlrenderer.js.html5.canvas.RenderingContext;
import org.xhtmlrenderer.js.impl.ElementImpl;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public class HTMLCanvasElementImpl extends ElementImpl implements HTMLCanvasElement {


    private final CanvasRenderingContext2DImpl context;

    public HTMLCanvasElementImpl(Element target, int width, int heigth, XHTMLPanel panel) {
        super(target, panel);
        context = new CanvasRenderingContext2DImpl(this, width, heigth);
    }

    @Override
    public Attribute<Integer> width() {
        return new Attribute<Integer>() {
            @Override
            public Integer get() {
                return context.getWidth();
            }

            @Override
            public void set(Integer integer) {
                context.resize(integer, context.getHeight());
            }
        };
    }

    @Override
    public Attribute<Integer> height() {
        return new Attribute<Integer>() {
            @Override
            public Integer get() {
                return context.getHeight();
            }

            @Override
            public void set(Integer integer) {
                context.resize(context.getWidth(), integer);
            }
        };    
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
