package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.Mixin;
import com.earnix.kbrowser.script.whatwg_dom.Element;

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
