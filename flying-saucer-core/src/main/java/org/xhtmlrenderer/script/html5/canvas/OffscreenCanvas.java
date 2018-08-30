package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.future.Blob;
import org.xhtmlrenderer.script.future.Promise;
import org.xhtmlrenderer.script.html5.ImageBitmap;
import org.xhtmlrenderer.script.web_idl.*;
import org.xhtmlrenderer.script.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Transferable
@Constructor(true)
public interface OffscreenCanvas extends EventTarget {

    void construct(@EnforceRange @Unsigned long width, @EnforceRange @Unsigned long height); // todo long long

    @Unsigned
    Attribute<Long> width(); // "long long" // todo

    @Unsigned
    Attribute<Long> height(); //  "long long";

    @Nullable
    OffscreenRenderingContext getContext(OffscreenRenderingContextId contextId, @Optional @DefaultNull Object options);

    ImageBitmap transferToImageBitmap();

    Promise<Blob> convertToBlob(@Optional ImageEncodeOptions options);
}
