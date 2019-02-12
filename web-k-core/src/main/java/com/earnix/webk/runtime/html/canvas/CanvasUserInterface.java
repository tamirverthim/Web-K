package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.dom.Element;

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
