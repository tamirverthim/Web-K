package org.xhtmlrenderer.js.html5.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.js.html5.DOMStringList;
import org.xhtmlrenderer.js.html5.Location;
import org.xhtmlrenderer.js.impl.USVStringImpl;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.js.whatwg_dom.Attr;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationImpl implements Location {

    XHTMLPanel panel;

    Attribute<USVString> href = Attribute
            .<USVString>receive(next -> {
                // todo
            })
            .give(() -> USVStringImpl.of(panel.getSharedContext().getBaseURL()));

    public LocationImpl(XHTMLPanel panel) {
        this.panel = panel;
    }

    @Override
    public Attribute<USVString> href() {
        return href;
    }

    @Override
    public USVString origin() {
        return null;
    }

    @Override
    public Attribute<USVString> protocol() {
        return Attribute
                .<USVString>receive(System.err::println)
                .give(() -> USVStringImpl.of(panel.getURL().getProtocol())
                );
    }

    @Override
    public Attribute<USVString> host() {
        return null;
    }

    @Override
    public Attribute<USVString> hostname() {
        return Attribute
                .<USVString>receive(System.err::println)
                .give(() -> USVStringImpl.of(panel.getURL().getHost())
                );
    }

    @Override
    public Attribute<USVString> port() {
        return null;
    }

    @Override
    public Attribute<USVString> pathname() {
        return null;
    }

    @Override
    public Attribute<USVString> search() {
        return null;
    }

    @Override
    public Attribute<USVString> hash() {
        return null;
    }

    @Override
    public void assign(USVString url) {
        panel.setDocument(url.toString());
    }

    @Override
    public void replace(USVString url) {
        panel.setDocument(url.toString());
    }

    @Override
    public void reload() {
        panel.reloadDocument(panel.getSharedContext().getBaseURL());
    }

    @Override
    public DOMStringList ancestorOrigins() {
        return null;
    }
}
