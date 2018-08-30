package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;
import org.xhtmlrenderer.script.whatwg_dom.Element;
import org.xhtmlrenderer.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
@HTMLConstructor
public interface HTMLCanvasElement extends Element {
    
    @CEReactions
    @Unsigned
    Attribute<Integer> width();

    @CEReactions
    @Unsigned
    Attribute<Integer> height();

    @Nullable
    RenderingContext getContext(@DOMString String contextId, @Optional @DefaultNull Object options);

    @USVString String toDataURL(@Optional @DOMString String type, @Optional Object quality);

    void toBlob(BlobCallback _callback, @Optional @DOMString String type, @Optional Object quality);

    OffscreenCanvas transferControlToOffscreen();
    
}
