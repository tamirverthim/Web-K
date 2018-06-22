package org.xhtmlrenderer.test;

import lombok.extern.slf4j.Slf4j;

import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;

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
    public boolean accept(LayoutContext context, org.jsoup.nodes.Element element) {
        return context.getNamespaceHandler().isCanvasElement(element);
    }

    @Override
    public ReplacedElement replace(LayoutContext context, BlockBox box, UserAgentCallback uac, int cssWidth, int cssHeight) {
        return null;//new CanvasReplacedElement(new CanvasRenderingContext2DImpl(context.));
    }

    @Override
    public void clear(org.jsoup.nodes.Element element) {
        log.warn("clear");
    }

    @Override
    public void reset() {
        log.warn("reset");
    }
    
}
