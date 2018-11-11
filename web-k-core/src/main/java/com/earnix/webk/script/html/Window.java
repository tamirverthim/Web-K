package com.earnix.webk.script.html;

import com.earnix.webk.script.console.Console;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.DefaultSequence;
import com.earnix.webk.script.web_idl.DefaultString;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Getter;
import com.earnix.webk.script.web_idl.Global;
import com.earnix.webk.script.web_idl.LegacyUnenumerableNamedProperties;
import com.earnix.webk.script.web_idl.NullTreat;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.PutForwards;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Replaceable;
import com.earnix.webk.script.web_idl.Sequence;
import com.earnix.webk.script.web_idl.TreatNullAs;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.web_idl.Unforgeable;
import com.earnix.webk.script.web_idl.Unsigned;
import com.earnix.webk.script.whatwg_dom.Element;

import java.util.Objects;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Exposed(Window.class) // todo we have multiple windows, fix it
@Global(Window.class)
public interface Window extends
        com.earnix.webk.script.whatwg_dom.Window,
        LegacyUnenumerableNamedProperties,
        GlobalEventHandlers,
        WindowOrWorkerGlobalScope {
    
    // the current browsing context
    @Unforgeable
    @ReadonlyAttribute
    WindowProxy window();

    @Replaceable
    @ReadonlyAttribute
    WindowProxy self();

    @Unforgeable
    @ReadonlyAttribute
    Document document();

    @DOMString Attribute<String> name();

    @PutForwards("href")
    @Unforgeable
    @ReadonlyAttribute
    Location location();

    @ReadonlyAttribute
    History history();

    @ReadonlyAttribute
    CustomElementRegistry customElements();

    @Replaceable
    @ReadonlyAttribute
    BarProp locationbar();

    @Replaceable
    @ReadonlyAttribute
    BarProp menubar();

    @Replaceable
    @ReadonlyAttribute
    BarProp personalbar();

    @Replaceable
    @ReadonlyAttribute
    BarProp scrollbars();

    @Replaceable
    @ReadonlyAttribute
    BarProp statusbar();

    @Replaceable
    @ReadonlyAttribute
    BarProp toolbar();

    @DOMString Attribute<String> status();

    void close();

    @ReadonlyAttribute
    boolean closed();

    void stop();

    void focus();

    void blur();

    // other browsing contexts
    @Replaceable
    @ReadonlyAttribute
    WindowProxy frames();

    @Replaceable
    @ReadonlyAttribute
    @Unsigned
    int length();

    @Unforgeable
    @ReadonlyAttribute
    @Nullable
    WindowProxy top();

    Attribute<Object> opener();

    @Replaceable
    @ReadonlyAttribute
    @Nullable
    WindowProxy parent();

    @ReadonlyAttribute
    @Nullable
    Element frameElement();

    @Nullable
    WindowProxy open(@Optional @USVString @DefaultString("about:blank") String url, @Optional @DOMString @DefaultString("_blank") String target, @Optional @TreatNullAs(NullTreat.EmptyString) @DefaultString("") @DOMString String features);

    @Getter
    Object get(@DOMString String name);
    // Since this is the global object, the IDL named getter adds a NamedPropertiesObject exotic
    // object on the prototype chain. Indeed, this does not make the global object an exotic object.
    // Indexed access is taken care of by the WindowProxy exotic object.

    // the user agent
    @ReadonlyAttribute
    Navigator navigator();

    @ReadonlyAttribute
    ApplicationCache applicationCache();

    // user prompts
    void alert();

    void alert(@DOMString String message);

    boolean confirm(@Optional @DOMString @DefaultString("") String message);

    @Nullable
    @DOMString String prompt(@Optional @DOMString @DefaultString("") String message, @Optional @DOMString @DefaultString("") String _default);

    void print();

    void postMessage(Object message, @USVString String targetOrigin, @Optional @DefaultSequence Sequence<Object> transfer);

    void postMessage(Object message, @Optional WindowPostMessageOptions options);

    // is not in WebIDL, exposed as namespace

    Console console();
}
