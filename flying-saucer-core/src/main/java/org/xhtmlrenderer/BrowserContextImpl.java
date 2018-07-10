package org.xhtmlrenderer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.js.JS;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrowserContextImpl implements BrowserContext {

    XHTMLPanel panel;
    JS js;
    Document parsedDocument;
    String uri;

    public BrowserContextImpl(XHTMLPanel panel, JS js, Document parsedDocument, String uri) {
        this.panel = panel;
        this.js = js;
        this.parsedDocument = parsedDocument;
        this.uri = uri;
    }

    @Override
    public XHTMLPanel getPanel() {
        return panel;
    }

    @Override
    public JS getJS() {
        return js;
    }

    @Override
    public String url() {
        return uri;
    }

    @Override
    public String documentUri() {
        return uri;
    }

    @Override
    public Document parsedDocument() {
        return parsedDocument;
    }

}
