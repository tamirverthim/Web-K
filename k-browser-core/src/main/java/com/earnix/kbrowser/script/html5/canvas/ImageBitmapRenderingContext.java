package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.html5.ImageBitmap;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Nullable;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;
import com.earnix.kbrowser.script.whatwg_dom.Window;

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
