package com.earnix.webk.script.html.canvas;

import com.earnix.webk.script.future.Blob;
import com.earnix.webk.script.web_idl.Callback;
import com.earnix.webk.script.web_idl.Nullable;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface BlobCallback {

    void call(@Nullable Blob blob);
}
