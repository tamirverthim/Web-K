package com.earnix.webk.script.xhr.impl;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.ByteString;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.whatwg_dom.Document;
import com.earnix.webk.script.whatwg_dom.Event;
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
import com.helger.commons.http.EHttpMethod;
import com.sun.java.browser.plugin2.DOM;
import lombok.AccessLevel;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpContext;
import sun.net.www.http.HttpClient;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public XMLHttpRequestImpl(){
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
        if (async) {
            executor.submit(this::sendImpl);
        } else {
            sendImpl();
        }
    }
    
    private void sendImpl(){
        executor.submit(() -> {
            HttpGet get = null;
            try {
                get = new HttpGet(url.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException();
            }

            HttpContext ctx = new HttpClientContext();
            headers.forEach((k,v) -> {
                ctx.setAttribute(k, v);
            });
            try (CloseableHttpResponse response = HTTP_CLIENT.execute(get, ctx)){
                
                SwingUtilities.invokeLater(() -> {
                    eventTarget.dispatchEvent(new EventImpl("loadstart", null));
                });
                
                this.response = IOUtils.toByteArray(response.getEntity().getContent());
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            SwingUtilities.invokeLater(() -> {
                eventTarget.dispatchEvent(new EventImpl("loadend", null));
            });
        });
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
    public @ByteString String statusText() {
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
        return level1EventTarget.getHanlderAttribute("onloadstart");
    }

    @Override
    public Attribute<EventHandler> onprogress() {
        return level1EventTarget.getHanlderAttribute("onprogress");
    }

    @Override
    public Attribute<EventHandler> onabort() {
        return level1EventTarget.getHanlderAttribute("onabort");
    }

    @Override
    public Attribute<EventHandler> onerror() {
        return level1EventTarget.getHanlderAttribute("onerror");
    }

    @Override
    public Attribute<EventHandler> onload() {
        return level1EventTarget.getHanlderAttribute("onload");
    }

    @Override
    public Attribute<EventHandler> ontimeout() {
        return level1EventTarget.getHanlderAttribute("ontimeout");
    }

    @Override
    public Attribute<EventHandler> onloadend() {
        return level1EventTarget.getHanlderAttribute("onloadend");
    }

    // endregion
    
    private void setReadyState(short readyState){
        this.readyState = readyState;
        val event = new EventImpl("readystatechange", null);
        eventTarget.dispatchEvent(event);
    }
}
