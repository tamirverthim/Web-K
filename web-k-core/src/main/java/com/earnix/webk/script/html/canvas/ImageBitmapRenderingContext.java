package com.earnix.webk.script.html.canvas;

import com.earnix.webk.script.html.ImageBitmap;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface ImageBitmapRenderingContext extends RenderingContext {
    @ReadonlyAttribute
    HTMLCanvasElement canvas();

    void transferFromImageBitmap(@Nullable ImageBitmap bitmap);
}
