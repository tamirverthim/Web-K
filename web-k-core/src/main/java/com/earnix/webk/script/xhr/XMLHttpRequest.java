package com.earnix.webk.script.xhr;

import com.earnix.webk.script.future.DedicatedWorker;
import com.earnix.webk.script.future.SharedWorker;
import com.earnix.webk.script.web_idl.Any;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.ByteString;
import com.earnix.webk.script.web_idl.Constructor;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.DefaultNull;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.SameObject;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.web_idl.Unsigned;
import com.earnix.webk.script.whatwg_dom.Document;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Constructor
@Exposed({Window.class, DedicatedWorker.class, SharedWorker.class})
public interface XMLHttpRequest extends XMLHttpRequestEventTarget {
    
    // event handler
    Attribute<EventHandler> onreadystatechange();

    // states
    @Unsigned short UNSENT = 0;
    @Unsigned short OPENED = 1;
    @Unsigned short HEADERS_RECEIVED = 2;
    @Unsigned short LOADING = 3;
    @Unsigned short DONE = 4;

    @ReadonlyAttribute
    @Unsigned
    short readyState();

    // request
    void open(@ByteString String method, @USVString String url);

    void open(@ByteString String method, @USVString String url, boolean async, @Optional @USVString @Nullable @DefaultNull String username, @Optional @USVString @DefaultNull String password);

    void setRequestHeader(@ByteString String name, @ByteString String value);

    @Unsigned
    Attribute<Integer> timeout();

    Attribute<Boolean> withCredentials();

    @SameObject
    @ReadonlyAttribute
    XMLHttpRequestUpload upload();

    /**
     * optional (ArrayBufferView or Blob or Document or ScalarValueString or FormData)? data = null
     * Supporting only string body for now
     */
    void send(@Optional @DefaultNull String data);

    void abort();

    // response
    @ReadonlyAttribute
    @USVString
    String responseURL();

    @ReadonlyAttribute
    @Unsigned
    short status();

    @ReadonlyAttribute
    @ByteString
    String
    statusText();

    @Nullable
    @ByteString
    String getResponseHeader(@ByteString String name);

    @ByteString
    String getAllResponseHeaders();

    void overrideMimeType(@DOMString String mime);

    Attribute<XMLHttpRequestResponseType> responseType();

    @ReadonlyAttribute
    @Any
    Object response();

    @ReadonlyAttribute
    @USVString
    String responseText();

    @Exposed(Window.class)
    @ReadonlyAttribute
    @Nullable
    Document responseXML();
}
