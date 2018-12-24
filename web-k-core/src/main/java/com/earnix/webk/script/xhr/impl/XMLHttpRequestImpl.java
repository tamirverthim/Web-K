package com.earnix.webk.script.xhr.impl;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.ByteString;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.whatwg_dom.Document;
import com.earnix.webk.script.whatwg_dom.EventHandler;
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
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class XMLHttpRequestImpl implements XMLHttpRequest {

    private final ScriptContext context;
    
    @Delegate(types = EventTarget.class)
    EventTargetImpl eventTarget;

    XMLHttpRequestUploadImpl upload;
    
    //    EventHandler onReadyStateChange;
    URL requestUrl;
    HttpMethod requestMethod;
    boolean async = true;
    HashMap<String, String> requestHeaders = new HashMap<>();
    /**
     * TODO Requires cookies implementation.
     */
    boolean withCredentials;
    int timeout;

    boolean send;

    static ExecutorService executor = Executors.newFixedThreadPool(10);

//    static CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    final Level1EventTarget level1EventTarget;

    byte[] response;
    String username;
    String password;
    String requestBody;

    boolean aborted;
    int loaded;
    int total;
    short readyState = UNSENT;

    HashMap<String, String> responseHeaders = new HashMap<>();
    ByteBuffer responseBytes;
    
    public XMLHttpRequestImpl(ScriptContext scriptContext) {
        this.context = scriptContext;
        eventTarget = new EventTargetImpl(scriptContext);
        level1EventTarget = new Level1EventTarget(context, this);
    }

    @Override
    public Attribute<EventHandler> onreadystatechange() {
        return level1EventTarget.getHandlerAttribute("onreadystatechange");
    }

    @Override
    public short readyState() {
        return readyState;
    }

    @Override
    public void open(@ByteString String method, @USVString String url) {
        open(method, url, true, null, null);
    }

    @Override
    public void open(@ByteString String method, @USVString String url, boolean async, String username, String password) {
        reset();
        
        try {
            this.requestMethod = HttpMethod.valueOf(method.trim().toUpperCase());
        } catch (NoSuchElementException e) {
            throw new DOMException("SyntaxError");
        }

        try {
            this.requestUrl = new URL(context.getPanel().getSharedContext().getUac().resolveURI(url));
        } catch (MalformedURLException e) {
            throw new DOMException("SyntaxError");
        }

        this.async = async;
        this.username = username;
        this.password = password;
        setReadyState(OPENED);
    }

    @Override
    public void setRequestHeader(@ByteString String name, @ByteString String value) {
        if (readyState == OPENED) {
            requestHeaders.put(name, value);
        } else {
            throw new DOMException("InvalidStateError");
        }
    }

    @Override
    public Attribute<Integer> timeout() {
        return new Attribute<Integer>() {
            @Override
            public Integer get() {
                return timeout;
            }

            @Override
            public void set(Integer timeout) {
                XMLHttpRequestImpl.this.timeout = timeout;
            }
        };
    }

    @Override
    public Attribute<Boolean> withCredentials() {
        return new Attribute<Boolean>() {
            @Override
            public Boolean get() {
                return withCredentials;
            }

            @Override
            public void set(Boolean aBoolean) {
                if (send || readyState != UNSENT && readyState != OPENED) {
                    throw new DOMException("InvalidStateError");
                }
            }
        };
    }

    @Override
    public XMLHttpRequestUpload upload() {
        return upload;
    }

    @Override
    public void send(String body) {
        if (send) {
            throw new DOMException("InvalidStateError");
        }
        this.requestBody = body;
        fireEvent("loadstart");
        if (async) {
            executor.submit(this::sendImpl);
        } else {
            sendImpl();
        }
    }

    private void reset() {
        send = false;
        responseHeaders.clear();
        response = null;
        loaded = 0;
        total = 0;
        setReadyState(UNSENT);
    }
    
    private void sendImpl() {
        try {
            if (requestMethod == HttpMethod.GET && requestUrl.getProtocol().equalsIgnoreCase("file")) {
                val allowedProperty = System.getProperty("com.earnix.eo.webk.network.xhr-file-url");
                if (allowedProperty != null && !Boolean.parseBoolean(allowedProperty)) {
                    throw new DOMException("Forbidden");
                }
                try (InputStream stream = requestUrl.openStream()) {
                    this.response = IOUtils.toByteArray(stream);
                }
            } else if (requestUrl.getProtocol().toLowerCase().startsWith("http")) {

                HttpRequestBase request = createRequest();
                RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectionRequestTimeout(timeout)
                        .setConnectTimeout(timeout)
                        .setSocketTimeout(timeout)
                        .setRedirectsEnabled(true)
                        .build();
                request.setConfig(requestConfig);
                

                val ctx = new HttpClientContext();

                if (username != null) {
                    val credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                    ctx.setCredentialsProvider(credentialsProvider);
                }

                requestHeaders.forEach(ctx::setAttribute);
                CloseableHttpClient httpClient = HttpClients.createDefault();

                try (CloseableHttpResponse response = httpClient.execute(request, ctx)) {

                    Stream.of(response.getAllHeaders()).forEach(header -> {
                        responseHeaders.put(header.getName(), header.getValue());
                    });

                    setReadyState(HEADERS_RECEIVED);

                    responseBytes = ByteBuffer.allocate(0);

                    try (val responseStream = response.getEntity().getContent()) {

                        setReadyState(LOADING);

                        total = (int) response.getEntity().getContentLength();
                        loaded = 0;
                        byte[] buffer = new byte[256];
                        int read;
                        while ((read = responseStream.read(buffer)) != 0) {
                            responseBytes.put(buffer, 0, read);
                            loaded += read;
                            val event = new ProgressEventImpl("progress", null);
                            event.setTotal(total);
                            event.setLoaded(loaded);
                            SwingUtilities.invokeLater(() -> eventTarget.dispatchEvent(event));
                            if (aborted) {
                                fireEvent("abort");
                                return;
                            }
                        }
                    }

                    setReadyState(DONE);
                    
                } catch (ConnectTimeoutException | SocketTimeoutException e) {

                    fireEvent("timeout");
                    setReadyState(DONE);
                    throw new DOMException("NetworkError");

                } catch (IOException e) {
                    setReadyState(DONE);
                    throw new DOMException("NetworkError");
                }
                
            } else {
                setReadyState(DONE);
                throw new DOMException("SyntaxError");
            }

            fireEvent("load");
            fireEvent("loadend");


        } catch (IOException e) {
            fireEvent("error");
            log.debug(e.getMessage(), e);
            fireEvent("loadend");
            throw new DOMException("NetworkError");
        }
    }

    private HttpRequestBase createRequest() {
        val urlString = requestUrl.toString();
        switch (requestMethod) {
            case GET:
                return new HttpGet(urlString);
            case PUT:
                return new HttpPut(urlString);
            case HEAD:
                return new HttpHead(urlString);
            case POST:
                val request = new HttpPost(urlString);
                if (requestBody != null) {
                    try {
                        request.setEntity(new StringEntity(requestBody));
                    } catch (UnsupportedEncodingException e) {
                        log.error(e.getMessage(), e);
                    }
                }
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
        aborted = true;
    }

    @Override
    public String responseURL() {
        return requestUrl.toString();
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
        return responseHeaders.get(name);
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
        return new Attribute<XMLHttpRequestResponseType>() {
            @Override
            public XMLHttpRequestResponseType get() {
                return null;
            }

            @Override
            public void set(XMLHttpRequestResponseType xmlHttpRequestResponseType) {

            }
        };
    }

    @Override
    public Object response() {
        return response;
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
            return new DocumentImpl(context, Jsoup.parse(new String(response, "UTF-8")), requestUrl.toString());
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

    private void fireEvent(String type) {
        val event = new ProgressEventImpl(type, null);
        event.setLoaded(loaded);
        event.setTotal(total);
        fireEvent(event);
    }

    private void fireEvent(EventImpl event) {
        if (SwingUtilities.isEventDispatchThread()) {
            eventTarget.dispatchEvent(event);
        } else {
            SwingUtilities.invokeLater(() -> fireEvent(event));
        }
    }

    private void setReadyState(short readyState) {
        this.readyState = readyState;
        val event = new EventImpl("readystatechange", null);
        fireEvent(event);
    }
}
