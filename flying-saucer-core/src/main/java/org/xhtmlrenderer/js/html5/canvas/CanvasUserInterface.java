package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.web_idl.Mixin;
import org.xhtmlrenderer.js.whatwg_dom.Element;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface CanvasUserInterface {

    void drawFocusIfNeeded(Element element);

    void drawFocusIfNeeded(Path2D path, Element element);

    void scrollPathIntoView();

    void scrollPathIntoView(Path2D path);

}
