package org.xhtmlrenderer.js.html5;

import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
public interface Location {
    @Unforgeable
    @Stringifier
    Attribute<USVString> href();

    @Unforgeable
    @ReadonlyAttribute
    USVString origin();

    @Unforgeable
    Attribute<USVString> protocol();

    @Unforgeable
    Attribute<USVString> host();

    @Unforgeable
    Attribute<USVString> hostname();

    @Unforgeable
    Attribute<USVString> port();

    @Unforgeable
    Attribute<USVString> pathname();

    @Unforgeable
    Attribute<USVString> search();

    @Unforgeable
    Attribute<USVString> hash();

    @Unforgeable
    void assign(USVString url);

    @Unforgeable
    void replace(USVString url);

    @Unforgeable
    void reload();

    @Unforgeable
    @SameObject
    @ReadonlyAttribute
    DOMStringList ancestorOrigins();
}
