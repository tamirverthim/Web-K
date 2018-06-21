package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.html5.ImageBitmap;
import org.xhtmlrenderer.js.web_idl.Exposed;
import org.xhtmlrenderer.js.web_idl.Nullable;
import org.xhtmlrenderer.js.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.js.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface ImageBitmapRenderingContext extends RenderingContext{
    @ReadonlyAttribute
    HTMLCanvasElement canvas();

    void transferFromImageBitmap(@Nullable ImageBitmap bitmap);
}
