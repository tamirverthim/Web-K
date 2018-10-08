package com.earnix.kbrowser.script.html5.canvas.impl;

import com.earnix.kbrowser.dom.nodes.Element;
import com.earnix.kbrowser.script.html5.canvas.BlobCallback;
import com.earnix.kbrowser.script.html5.canvas.HTMLCanvasElement;
import com.earnix.kbrowser.script.html5.canvas.OffscreenCanvas;
import com.earnix.kbrowser.script.html5.canvas.RenderingContext;
import com.earnix.kbrowser.script.impl.ElementImpl;
import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.USVString;
import com.earnix.kbrowser.swing.BasicPanel;
import com.earnix.kbrowser.util.GeneralUtil;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public class HTMLCanvasElementImpl extends ElementImpl implements HTMLCanvasElement {


    private final CanvasRenderingContext2DImpl context;

    public HTMLCanvasElementImpl(Element target, BasicPanel panel) {
        super(target, panel);
        if (!target.hasAttr("width")) {
            target.attr("width", String.valueOf(300));
        }
        if (!target.hasAttr("height")) {
            target.attr("height", String.valueOf(150));
        }
        context = new CanvasRenderingContext2DImpl(this);
    }

    @Override
    public Attribute<Integer> width() {
        return new Attribute<Integer>() {
            @Override
            public Integer get() {
                return GeneralUtil.parseIntRelaxed(getModel().attr("width"));
            }

            @Override
            public void set(Integer integer) {
                getModel().attr("width", String.valueOf(integer));
                context.resize();
            }
        };
    }

    @Override
    public Attribute<Integer> height() {
        return new Attribute<Integer>() {
            @Override
            public Integer get() {
                return GeneralUtil.parseIntRelaxed(getModel().attr("height"));
            }

            @Override
            public void set(Integer integer) {
                getModel().attr("height", String.valueOf(integer));
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
        return context;
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
        return context;
    }
}
