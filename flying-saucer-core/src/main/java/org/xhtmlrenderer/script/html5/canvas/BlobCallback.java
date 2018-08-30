package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.future.Blob;
import org.xhtmlrenderer.script.web_idl.Callback;
import org.xhtmlrenderer.script.web_idl.Nullable;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface BlobCallback {

    void call(@Nullable Blob blob);
}
