package com.earnix.webk.script.xhr.impl;

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
import com.earnix.webk.script.whatwg_dom.impl.EventTargetImpl;
import com.earnix.webk.script.xhr.XMLHttpRequest;
import com.earnix.webk.script.xhr.XMLHttpRequestResponseType;
import com.earnix.webk.script.xhr.XMLHttpRequestUpload;
import com.helger.commons.http.EHttpMethod;
import com.sun.java.browser.plugin2.DOM;
import lombok.AccessLevel;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpRequest;
import sun.net.www.http.HttpClient;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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
    String method;
    boolean async = true;
    
    static ExecutorService executor = Executors.newFixedThreadPool(10);

    static CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

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
        this.method = method;
        if (method == null) {
            throw new DOMException("Unknown request method");
        }
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new DOMException("Malformed URL");
        }
    }

    @Override
    public void open(@ByteString String method, @USVString String url, boolean async, String username, String password) {

    }

    @Override
    public void setRequestHeader(@ByteString String name, @ByteString String value) {

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
            executor.submit(() -> {
                HttpGet get = null;
                try {
                    get = new HttpGet(url.toURI());
                } catch (URISyntaxException e) {
                    throw new RuntimeException();
                }
                CloseableHttpResponse response = null;
                
                try {
                    response = HTTP_CLIENT.execute(get);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
                SwingUtilities.invokeLater(() -> {
                  
                });
            });
            
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
        return null;
    }

    @Override
    public Document responseXML() {
        return null;
    }


    // region -- EventTarget --

    @Override
    public Attribute<EventHandler> onloadstart() {
        return null;
    }

    @Override
    public Attribute<EventHandler> onprogress() {
        return null;
    }

    @Override
    public Attribute<EventHandler> onabort() {
        return null;
    }

    @Override
    public Attribute<EventHandler> onerror() {
        return null;
    }

    @Override
    public Attribute<EventHandler> onload() {
        return null;
    }

    @Override
    public Attribute<EventHandler> ontimeout() {
        return null;
    }

    @Override
    public Attribute<EventHandler> onloadend() {
        return null;
    }

    // endregion
}
