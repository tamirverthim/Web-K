package org.xhtmlrenderer.test;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.js.canvas.impl.CanvasRenderingContext2DImpl;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.swing.*;
import org.xhtmlrenderer.util.ImageUtil;
import org.xhtmlrenderer.util.XRLog;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@Slf4j
public class CanvasElementReplacer extends ElementReplacer {
    
    @Override
    public boolean isElementNameMatch() {
        return true;
    }

    @Override
    public String getElementNameMatch() {
        return "canvas";
    }

    @Override
    public boolean accept(LayoutContext context, Element element) {
        return context.getNamespaceHandler().isCanvasElement(element);
    }

    @Override
    public ReplacedElement replace(LayoutContext context, BlockBox box, UserAgentCallback uac, int cssWidth, int cssHeight) {
        return null;//new CanvasReplacedElement(new CanvasRenderingContext2DImpl(context.));
    }

    @Override
    public void clear(Element element) {
        log.warn("clear");
    }

    @Override
    public void reset() {
        log.warn("reset");
    }
    
}
