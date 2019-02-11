package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.future.Blob;
import com.earnix.webk.runtime.future.Promise;
import com.earnix.webk.runtime.html.ImageBitmap;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Constructor;
import com.earnix.webk.runtime.web_idl.DefaultNull;
import com.earnix.webk.runtime.web_idl.EnforceRange;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.Transferable;
import com.earnix.webk.runtime.web_idl.Unsigned;
import com.earnix.webk.runtime.whatwg_dom.EventTarget;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Transferable
@Constructor
public interface OffscreenCanvas extends EventTarget {

    void constructor(@EnforceRange @Unsigned long width, @EnforceRange @Unsigned long height); // todo long long

    @Unsigned
    Attribute<Long> width(); // "long long" // todo

    @Unsigned
    Attribute<Long> height(); //  "long long";

    @Nullable
    OffscreenRenderingContext getContext(OffscreenRenderingContextId contextId, @Optional @DefaultNull Object options);

    ImageBitmap transferToImageBitmap();

    Promise<Blob> convertToBlob(@Optional ImageEncodeOptions options);
}
