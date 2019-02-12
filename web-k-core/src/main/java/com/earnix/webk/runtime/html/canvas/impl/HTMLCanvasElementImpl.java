package com.earnix.webk.runtime.html.canvas.impl;

import com.earnix.webk.runtime.dom.impl.nodes.AttributesModel;
import com.earnix.webk.runtime.dom.impl.parser.Tag;
import com.earnix.webk.runtime.html.canvas.BlobCallback;
import com.earnix.webk.runtime.html.canvas.HTMLCanvasElement;
import com.earnix.webk.runtime.html.canvas.OffscreenCanvas;
import com.earnix.webk.runtime.html.canvas.RenderingContext;
import com.earnix.webk.runtime.dom.impl.ElementImpl;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.USVString;
import com.earnix.webk.util.GeneralUtil;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public class HTMLCanvasElementImpl extends ElementImpl implements HTMLCanvasElement {
    
    private CanvasRenderingContext2DImpl context;

    public HTMLCanvasElementImpl(Tag tag, String baseUri, AttributesModel attributes) {
        super(tag, baseUri, attributes);
        
        if (!hasAttr("width")) {
            attr("width", String.valueOf(300));
        }
        if (!hasAttr("height")) {
            attr("height", String.valueOf(150));
        }
    }
    
    @Override
    public Attribute<Integer> width() {
        return new Attribute<Integer>() {
            @Override
            public Integer get() {
                return GeneralUtil.parseIntRelaxed(attr("width"));
            }

            @Override
            public void set(Integer integer) {
                attr("width", String.valueOf(integer));
                context.resize();
            }
        };
    }

    @Override
    public Attribute<Integer> height() {
        return new Attribute<Integer>() {
            @Override
            public Integer get() {
                return GeneralUtil.parseIntRelaxed(attr("height"));
            }

            @Override
            public void set(Integer integer) {
                attr("height", String.valueOf(integer));
                context.resize();
            }
        };
    }

    @Override
    public void setAttribute(@DOMString String qualifiedName, @DOMString String value) {
        super.setAttribute(qualifiedName, value);
        if (qualifiedName.equalsIgnoreCase("width") || qualifiedName.equalsIgnoreCase("height")) {
            context.resize();
        }
    }

    @Override
    public RenderingContext getContext(@DOMString String contextId, Object options) {
        return getContextImpl();
    }

    @Override
    public @USVString
    String toDataURL(@DOMString String type, Object quality) {
        return null;
    }

    @Override
    public void toBlob(BlobCallback _callback, @DOMString String type, Object quality) {

    }

    @Override
    public OffscreenCanvas transferControlToOffscreen() {
        return null;
    }

    public CanvasRenderingContext2DImpl getContextImpl() {
        if (context == null) {
            context = new CanvasRenderingContext2DImpl(this);
        }
        return context;
    }
}
