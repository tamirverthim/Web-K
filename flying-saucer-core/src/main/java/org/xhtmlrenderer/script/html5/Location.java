package org.xhtmlrenderer.script.html5;

import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
public interface Location {
    
    @Unforgeable
    @Stringifier
    @USVString
    Attribute<String> href();

    @Unforgeable
    @ReadonlyAttribute
    @USVString String origin();

    @Unforgeable
    @USVString
    Attribute<String> protocol();

    @Unforgeable
    @USVString
    Attribute<String> host();

    @Unforgeable
    @USVString
    Attribute<String> hostname();

    @Unforgeable
    @USVString
    Attribute<String> port();

    @Unforgeable
    @USVString
    Attribute<String> pathname();

    @Unforgeable
    @USVString
    Attribute<String> search();

    @Unforgeable
    @USVString
    Attribute<String> hash();

    @Unforgeable
    void assign(@USVString String url);

    @Unforgeable
    void replace(@USVString String url);

    @Unforgeable
    void reload();

    @Unforgeable
    @SameObject
    @ReadonlyAttribute
    DOMStringList ancestorOrigins();
}
