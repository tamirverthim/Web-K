package com.earnix.webk.script.html;

import com.earnix.webk.script.fetch.RequestInfo;
import com.earnix.webk.script.fetch.RequestInit;
import com.earnix.webk.script.fetch.Response;
import com.earnix.webk.script.future.Promise;
import com.earnix.webk.script.web_idl.ByteString;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.DefaultLong;
import com.earnix.webk.script.web_idl.Mixin;
import com.earnix.webk.script.web_idl.NewObject;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.Readonly;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Replaceable;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.web_idl.VoidFunction;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@Mixin
public interface WindowOrWorkerGlobalScope {

    // https://fetch.spec.whatwg.org/#idl-index
    @NewObject
    Promise<Response> fetch(RequestInfo input, @Optional RequestInit init);


    @Replaceable
    @ReadonlyAttribute
    @USVString
    String origin();

    // base64 utility methods
    @DOMString String btoa(@DOMString String data);

    @ByteString
    String atob(@DOMString String data);

    // timers
    int setTimeout(TimerHandler handler, @Optional @DefaultLong(0) int timeout, Object... arguments);

    void clearTimeout(@Optional @DefaultLong(0) int handle);

    int setInterval(TimerHandler handler, @Optional @DefaultLong(0) int timeout, Object... arguments);

    void clearInterval(@Optional @DefaultLong(0) int handle);

    // microtask queuing
    void queueMicrotask(VoidFunction callback);

    // ImageBitmap
    Promise<ImageBitmap> createImageBitmap(ImageBitmapSource image, @Optional ImageBitmapOptions options);

    Promise<ImageBitmap> createImageBitmap(ImageBitmapSource image, long sx, long sy, long sw, long sh, @Optional ImageBitmapOptions options);
}
