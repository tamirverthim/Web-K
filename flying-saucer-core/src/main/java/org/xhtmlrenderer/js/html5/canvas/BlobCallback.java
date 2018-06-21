package org.xhtmlrenderer.js.html5.canvas;

import org.xhtmlrenderer.js.future.Blob;
import org.xhtmlrenderer.js.web_idl.Callback;
import org.xhtmlrenderer.js.web_idl.Nullable;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface BlobCallback {

    void call(@Nullable Blob blob);
}
