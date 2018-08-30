package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.html5.ImageBitmap;
import org.xhtmlrenderer.script.web_idl.Exposed;
import org.xhtmlrenderer.script.web_idl.Nullable;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;
import org.xhtmlrenderer.script.whatwg_dom.Window;

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
