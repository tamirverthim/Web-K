package com.earnix.webk.script.html5;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.CEReactions;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.DefaultBoolean;
import com.earnix.webk.script.web_idl.DefaultString;
import com.earnix.webk.script.web_idl.Getter;
import com.earnix.webk.script.web_idl.LenientThis;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.OverrideBuiltins;
import com.earnix.webk.script.web_idl.PutForwards;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.SameObject;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.web_idl.Unforgeable;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.HTMLCollection;
import com.earnix.webk.script.whatwg_dom.NodeList;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@OverrideBuiltins
public interface Document {

    // TODO
    //Document includes GlobalEventHandlers;
    //Document includes DocumentAndElementEventHandlers;

    // resource metadata management

    @PutForwards("href")
    @Unforgeable
    @ReadonlyAttribute
    @Nullable
    Location location();

    @USVString
    Attribute<String> domain();

    @ReadonlyAttribute
    @USVString
    String referrer();

    @USVString
    Attribute<String> cookie();

    @ReadonlyAttribute
    @DOMString String lastModified();

    @ReadonlyAttribute
    DocumentReadyState readyState();

    // DOM tree accessors

    @Getter
    Object object(String name);

    @CEReactions
    @DOMString
    Attribute<String> title();

    @CEReactions
    Attribute<String> dir();

    @CEReactions
    @Nullable
    Attribute<HTMLElement> body();

    @ReadonlyAttribute
    @Nullable
    HTMLHeadElement head();

    @SameObject
    @ReadonlyAttribute
    HTMLCollection images();

    @SameObject
    @ReadonlyAttribute
    HTMLCollection embeds();

    @SameObject
    @ReadonlyAttribute
    HTMLCollection plugins();

    @SameObject
    @ReadonlyAttribute
    HTMLCollection links();

    @SameObject
    @ReadonlyAttribute
    HTMLCollection forms();

    @SameObject
    @ReadonlyAttribute
    HTMLCollection scripts();

    NodeList getElementsByName(@DOMString String elementName);

    @Nullable
    @ReadonlyAttribute
    HTMLOrSVGScriptElement currentScript(); // classic scripts in a document tree only

    // dynamic markup insertion
    @CEReactions
    Document open(@Optional @DOMString String type, @Optional @DefaultString("") @DOMString String replace); // type is ignored

    @WindowProxy
    Object open(@USVString String url, @DOMString String name, @DOMString String features);

    @CEReactions
    void close();

    @CEReactions
    void write(@DOMString String... text);

    @CEReactions
    void writeln(@DOMString String... text);

    // user interaction
    @ReadonlyAttribute
    @Nullable
    @WindowProxy
    Object defaultView();

    @ReadonlyAttribute
    @Nullable
    Element activeElement();

    boolean hasFocus();

    @CEReactions
    @DOMString
    Attribute<String> designMode();

    @CEReactions
    boolean execCommand(@DOMString String commandId, @Optional @DefaultBoolean(false) boolean showUI, @Optional @DefaultString("") @DOMString String value);

    boolean queryCommandEnabled(@DOMString String commandId);

    boolean queryCommandIndeterm(@DOMString String commandId);

    boolean queryCommandState(@DOMString String commandId);

    boolean queryCommandSupported(@DOMString String commandId);

    @DOMString String queryCommandValue(@DOMString String commandId);

    // special event handler IDL attributes that only apply to Document objects
    @LenientThis
    Attribute<EventHandler> onreadystatechange();
}
