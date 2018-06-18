package org.xhtmlrenderer.js.canvas.impl;

import org.xhtmlrenderer.js.canvas.CanvasRenderingContext2D;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.dom.impl.ElementImpl;
import org.xhtmlrenderer.js.canvas.HTMLCanvasElement;
import org.xhtmlrenderer.js.web_idl.Attribute;

import static java.lang.Integer.parseInt;

/**
 * @author Taras Maslov
 * 6/4/2018
 */
public class HTMLCanvasElementImpl extends ElementImpl implements HTMLCanvasElement {

    private CanvasRenderingContext2DImpl context;

    public HTMLCanvasElementImpl(org.jsoup.nodes.Element target) {
        super(target);
        context = new CanvasRenderingContext2DImpl(
                parseInt(target.attr("width")), 
                parseInt(target.attr("height"))
        );
    }

    @Override
    public Attribute<Long> width() {
        return Attribute.readOnly(100L);
    }

    @Override
    public Attribute<Long> height() {
        return Attribute.readOnly(100L);
    }

    @Override
    public DOMString toDataURL(DOMString type, Object... args) {
        return null;
    }

    @Override
    public CanvasRenderingContext2D getContext(DOMString contextId) {
        return context;
    }

    public CanvasRenderingContext2DImpl getContextImpl() {
        return context;
    }
}
