package org.xhtmlrenderer.js.html5;

import org.xhtmlrenderer.js.web_idl.*;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.EventHandler;
import org.xhtmlrenderer.js.whatwg_dom.HTMLCollection;
import org.xhtmlrenderer.js.whatwg_dom.NodeList;

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

    Attribute<USVString> domain();

    @ReadonlyAttribute
    USVString referrer();

    Attribute<USVString> cookie();

    @ReadonlyAttribute
    DOMString lastModified();

    @ReadonlyAttribute
    DocumentReadyState readyState();

    // DOM tree accessors

    @Getter
    Object object(DOMString name);

    @CEReactions
    Attribute<DOMString> title();

    @CEReactions
    Attribute<DOMString> dir();

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

    NodeList getElementsByName(DOMString elementName);

    @Nullable
    @ReadonlyAttribute
    HTMLOrSVGScriptElement currentScript(); // classic scripts in a document tree only

    // dynamic markup insertion
    @CEReactions
    Document open(@Optional DOMString type, @Optional @DefaultString("") DOMString replace); // type is ignored

    WindowProxy open(USVString url, DOMString name, DOMString features);

    @CEReactions
    void close();

    @CEReactions
    void write(DOMString... text);

    @CEReactions
    void writeln(DOMString... text);

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
    Attribute<DOMString> designMode();

    @CEReactions
    boolean execCommand(DOMString commandId, @Optional @DefaultBoolean(false) boolean showUI, @Optional @DefaultString("") DOMString value);

    boolean queryCommandEnabled(DOMString commandId);

    boolean queryCommandIndeterm(DOMString commandId);

    boolean queryCommandState(DOMString commandId);

    boolean queryCommandSupported(DOMString commandId);

    DOMString queryCommandValue(DOMString commandId);

    // special event handler IDL attributes that only apply to Document objects
    @LenientThis
    Attribute<EventHandler> onreadystatechange();
}
