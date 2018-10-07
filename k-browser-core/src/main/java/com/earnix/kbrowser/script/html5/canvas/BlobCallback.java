package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.future.Blob;
import com.earnix.kbrowser.script.web_idl.Callback;
import com.earnix.kbrowser.script.web_idl.Nullable;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Callback
public interface BlobCallback {

    void call(@Nullable Blob blob);
}
