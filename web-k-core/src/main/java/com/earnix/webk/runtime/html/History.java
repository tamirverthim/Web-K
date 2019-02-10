package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.DefaultLong;
import com.earnix.webk.runtime.web_idl.DefaultNull;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.USVString;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Exposed(Window.class)
public interface History {
    @ReadonlyAttribute
    @Unsigned
    int length();

    Attribute<ScrollRestoration> scrollRestoration();

    @ReadonlyAttribute
    Object state();

    void go(@Optional @DefaultLong(0) int delta);

    void back();

    void forward();

    void pushState(Object data, @DOMString String title, @Optional @Nullable @DefaultNull @USVString String url);

    void replaceState(Object data, @DOMString String title, @Optional @Nullable @USVString @DefaultNull String url);
}
