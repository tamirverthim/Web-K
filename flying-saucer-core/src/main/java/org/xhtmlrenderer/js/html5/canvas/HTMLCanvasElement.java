package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.*;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.Window;

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
    RenderingContext getContext(DOMString contextId, @Optional @DefaultNull Object options);

    USVString toDataURL(@Optional DOMString type, @Optional Object quality);

    void toBlob(BlobCallback _callback, @Optional DOMString type, @Optional Object quality);

    OffscreenCanvas transferControlToOffscreen();
}
