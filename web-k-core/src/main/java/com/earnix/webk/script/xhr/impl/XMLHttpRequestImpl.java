package com.earnix.webk.script.xhr.impl;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.ByteString;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.whatwg_dom.Document;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.EventListener;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import com.earnix.webk.script.whatwg_dom.impl.DocumentImpl;
import com.earnix.webk.script.whatwg_dom.impl.EventImpl;
import com.earnix.webk.script.whatwg_dom.impl.EventTargetImpl;
import com.earnix.webk.script.whatwg_dom.impl.Level1EventTarget;
import com.earnix.webk.script.xhr.XMLHttpRequest;
import com.earnix.webk.script.xhr.XMLHttpRequestResponseType;
import com.earnix.webk.script.xhr.XMLHttpRequestUpload;
import lombok.AccessLevel;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class XMLHttpRequestImpl implements XMLHttpRequest {

    @Delegate(types = EventTarget.class)
    EventTargetImpl eventTarget = new EventTargetImpl();
    short readyState = 0;

    EventHandler onReadyStateChange;
    URL url;
    HttpMethod method;
    boolean async = true;
    HashMap<String, String> headers = new HashMap<>();

    static ExecutorService executor = Executors.newFixedThreadPool(10);

    static CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    Level1EventTarget level1EventTarget = new Level1EventTarget(this);

    byte[] response;
    String username;
    String password;
    private Object body;

    public XMLHttpRequestImpl() {
        EventListener onReadyStateChangeListener = event -> {
            if (onReadyStateChange != null) {
                onReadyStateChange.call(event);
            }
        };
        eventTarget.addEventListener("onreadystatechange", onReadyStateChangeListener, null);
    }

    @Override
    public Attribute<EventHandler> onreadystatechange() {
        return new Attribute<EventHandler>() {
            @Override
            public EventHandler get() {
                return onReadyStateChange;
            }

            @Override
            public void set(EventHandler eventHandler) {
                onReadyStateChange = eventHandler;
            }
        };
    }

    @Override
    public short readyState() {
        return 0;
    }

    @Override
    public void open(@ByteString String method, @USVString String url) {
        open(method, url, false, null, null);
    }

    @Override
    public void open(@ByteString String method, @USVString String url, boolean async, String username, String password) {
        try {
            this.method = HttpMethod.valueOf(method.trim().toUpperCase());
        } catch (NoSuchElementException e) {
            throw new DOMException("Unknown request method");
        }

        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new DOMException("Malformed URL");
        }

        this.async = async;
        this.username = username;
        this.password = password;
        setReadyState(OPENED);
    }

    @Override
    public void setRequestHeader(@ByteString String name, @ByteString String value) {
        headers.put(name, value);
    }

    @Override
    public Attribute<Integer> timeout() {
        return null;
    }

    @Override
    public Attribute<Boolean> withCredentials() {
        return null;
    }

    @Override
    public XMLHttpRequestUpload upload() {
        return null;
    }

    @Override
    public void send(Object body) {
        this.body = body;
        if (async) {
            executor.submit(this::sendImpl);
        } else {
            sendImpl();
        }
    }

    private void sendImpl() {

        HttpRequestBase request = null;

        request = createRequest();

        HttpContext ctx = new HttpClientContext();
        headers.forEach(ctx::setAttribute);
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request, ctx)) {

            SwingUtilities.invokeLater(() -> {
                eventTarget.dispatchEvent(new EventImpl("loadstart", null));
            });

            this.response = IOUtils.toByteArray(response.getEntity().getContent());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SwingUtilities.invokeLater(() -> {
            val loadEvent = new EventImpl("load", null);
            loadEvent.setTarget(XMLHttpRequestImpl.this);
            eventTarget.dispatchEvent(loadEvent);

            val loadEndEvent = new EventImpl("loadend", null);
            loadEvent.setTarget(XMLHttpRequestImpl.this);
            eventTarget.dispatchEvent(loadEndEvent);
        });

    }

    private HttpRequestBase createRequest() {
        val urlString = url.toString();
        switch (method) {
            case GET:
                return new HttpGet(urlString);
            case PUT:
                return new HttpPut(urlString);
            case HEAD:
                return new HttpHead(urlString);
            case POST:
                return new HttpPost(urlString);
            case TRACE:
                return new HttpTrace(urlString);
            case DELETE:
                return new HttpDelete(urlString);
            case CONNECT:
                throw new DOMException("Unsupported method");
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void abort() {

    }

    @Override
    public String responseURL() {
        return null;
    }

    @Override
    public short status() {
        return 0;
    }

    @Override
    public @ByteString
    String statusText() {
        return null;
    }

    @Override
    public String getResponseHeader(String name) {
        return null;
    }

    @Override
    public @ByteString
    String getAllResponseHeaders() {
        return null;
    }

    @Override
    public void overrideMimeType(@DOMString String mime) {

    }

    @Override
    public Attribute<XMLHttpRequestResponseType> responseType() {
        return null;
    }

    @Override
    public Object response() {
        return null;
    }

    @Override
    public String responseText() {
        try {
            return new String(response, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Document responseXML() {
        try {
            return new DocumentImpl(Jsoup.parse(new String(response, "UTF-8")), url.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    // region -- EventTarget --

    @Override
    public Attribute<EventHandler> onloadstart() {
        return level1EventTarget.getHandlerAttribute("onloadstart");
    }

    @Override
    public Attribute<EventHandler> onprogress() {
        return level1EventTarget.getHandlerAttribute("onprogress");
    }

    @Override
    public Attribute<EventHandler> onabort() {
        return level1EventTarget.getHandlerAttribute("onabort");
    }

    @Override
    public Attribute<EventHandler> onerror() {
        return level1EventTarget.getHandlerAttribute("onerror");
    }

    @Override
    public Attribute<EventHandler> onload() {
        return level1EventTarget.getHandlerAttribute("onload");
    }

    @Override
    public Attribute<EventHandler> ontimeout() {
        return level1EventTarget.getHandlerAttribute("ontimeout");
    }

    @Override
    public Attribute<EventHandler> onloadend() {
        return level1EventTarget.getHandlerAttribute("onloadend");
    }

    // endregion

    private void setReadyState(short readyState) {
        this.readyState = readyState;
        val event = new EventImpl("readystatechange", null);
        eventTarget.dispatchEvent(event);
    }
}
