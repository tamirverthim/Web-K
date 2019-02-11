package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.future.Blob;
import com.earnix.webk.runtime.web_idl.Callback;
import com.earnix.webk.runtime.web_idl.Nullable;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface BlobCallback {

    void call(@Nullable Blob blob);
}
