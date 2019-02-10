package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.SameObject;
import com.earnix.webk.runtime.web_idl.Stringifier;
import com.earnix.webk.runtime.web_idl.USVString;
import com.earnix.webk.runtime.web_idl.Unforgeable;

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
    @USVString
    String origin();

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
