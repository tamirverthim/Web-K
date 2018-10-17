package com.earnix.webk.script.html.impl;

import com.earnix.webk.script.html.DOMStringList;
import com.earnix.webk.script.html.Location;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationImpl implements Location {

    BasicPanel panel;

    Attribute<String> href = new Attribute<String>() {

        @Override
        public String get() {
            return panel.getSharedContext().getBaseURL();
        }

        @Override
        public void set(String s) {

        }

    };

    LocationImpl(BasicPanel panel) {
        this.panel = panel;
    }

    @Override
    public Attribute<String> href() {
        return href;
    }

    @Override
    public @USVString
    String origin() {
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
