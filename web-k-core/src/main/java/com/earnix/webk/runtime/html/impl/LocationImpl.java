package com.earnix.webk.runtime.html.impl;

import com.earnix.webk.runtime.html.DOMStringList;
import com.earnix.webk.runtime.html.Location;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.USVString;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationImpl implements Location {

    DocumentImpl doc;

    Attribute<String> href = new Attribute<String>() {

        @Override
        public String get() {
            return doc.scriptContext().getPanel().getSharedContext().getBaseURL();
        }

        @Override
        public void set(String s) {

        }

    };

    LocationImpl(DocumentImpl doc) {
        this.doc = doc;
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
                .give(() -> doc.scriptContext().getPanel().getURL().getProtocol());
    }

    @Override
    public Attribute<String> host() {
        return null;
    }

    @Override
    public Attribute<String> hostname() {
        return Attribute
                .<String>receive(System.err::println)
                .give(() -> doc.scriptContext().getPanel().getURL().getHost());
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
        doc.scriptContext().getPanel().setDocument(url);
    }

    @Override
    public void replace(@USVString String url) {
        doc.scriptContext().getPanel().setDocument(url);
    }

    @Override
    public void reload() {
        doc.scriptContext().getPanel().reloadDocument(doc.scriptContext().getPanel().getSharedContext().getBaseURL());
    }

    @Override
    public DOMStringList ancestorOrigins() {
        return null;
    }
}
