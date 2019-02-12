package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.html.ImageBitmap;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.dom.Window;

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
