package org.xhtmlrenderer.script.html5.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.script.html5.DOMStringList;
import org.xhtmlrenderer.script.html5.Location;
import org.xhtmlrenderer.script.web_idl.Attribute;
import org.xhtmlrenderer.script.web_idl.USVString;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationImpl implements Location {

    XHTMLPanel panel;

    Attribute<String> href = new Attribute<String>() {
        
        @Override
        public String get() {
            return panel.getSharedContext().getBaseURL();
        }

        @Override
        public void set(String s) {

        }
        
    };

    LocationImpl(XHTMLPanel panel) {
        this.panel = panel;
    }

    @Override
    public Attribute<String> href() {
        return href;
    }

    @Override
    public @USVString String origin() {
        return null;
    }

    @Override
    public Attribute<String> protocol() {
        return Attribute.<String>receive(System.err::println)
                .give(() -> panel.getURL().getProtocol());
    }

    @Override
    public Attribute<String> host() {
        return null;
    }

    @Override
    public Attribute<String> hostname() {
        return Attribute
                .<String>receive(System.err::println)
                .give(() -> panel.getURL().getHost());
    }

    @Override
    public Attribute<String> port() {
        return null;
    }

    @Override
    public Attribute<String> pathname() {
        return null;
    }

    @Override
    public Attribute<String> search() {
        return null;
    }

    @Override
    public Attribute<String> hash() {
        return null;
    }

    @Override
    public void assign(@USVString String url) {
        panel.setDocument(url);
    }

    @Override
    public void replace(@USVString String url) {
        panel.setDocument(url);
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
