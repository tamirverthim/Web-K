package org.xhtmlrenderer.script.html5;

import org.xhtmlrenderer.script.web_idl.*;
import org.xhtmlrenderer.script.whatwg_dom.Element;
import org.xhtmlrenderer.script.whatwg_dom.EventHandler;
import org.xhtmlrenderer.script.whatwg_dom.HTMLCollection;
import org.xhtmlrenderer.script.whatwg_dom.NodeList;

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
    @USVString String referrer();

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

    @WindowProxy Object open(@USVString String url, @DOMString String name, @DOMString String features);

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
