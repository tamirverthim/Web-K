package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.*;
import org.xhtmlrenderer.js.whatwg_dom.css_style_attribute.CSSStyleAttribute;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
public interface Element extends Node, ParentNode, NonDocumentTypeChildNode, ChildNode, Slotable {
    @ReadonlyAttribute
    @Nullable
    DOMString namespaceURI();

    @ReadonlyAttribute
    @Nullable
    DOMString prefix();

    @ReadonlyAttribute
    DOMString localName();

    @ReadonlyAttribute
    DOMString tagName();

    @CEReactions
    Attribute<DOMString> id();

    @CEReactions
    Attribute<DOMString> className();

    @SameObject
    @PutForwards("value")
    @ReadonlyAttribute
    DOMTokenList classList();

    @CEReactions
    @Unscopable
    Attribute<DOMString> slot();

    boolean hasAttributes();

    @SameObject
    @ReadonlyAttribute
    NamedNodeMap attributes();

    Sequence<DOMString> getAttributeNames();

    @Nullable
    DOMString getAttribute(DOMString qualifiedName);

    @Nullable
    DOMString getAttributeNS(@Nullable DOMString namespace, DOMString localName);

    @CEReactions
    void setAttribute(DOMString qualifiedName, DOMString value);

    @CEReactions
    void setAttributeNS(@Nullable DOMString namespace, DOMString qualifiedName, DOMString value);

    @CEReactions
    void removeAttribute(DOMString qualifiedName);

    @CEReactions
    void removeAttributeNS(@Nullable DOMString namespace, DOMString localName);

    @CEReactions
    boolean toggleAttribute(DOMString qualifiedName, @Optional Boolean force);

    boolean hasAttribute(DOMString qualifiedName);

    boolean hasAttributeNS(@Nullable DOMString namespace, DOMString localName);

    @Nullable
    Attr getAttributeNode(DOMString qualifiedName);

    @Nullable
    Attr getAttributeNodeNS(@Nullable DOMString namespace, DOMString localName);

    @Nullable
    @CEReactions
    Attr setAttributeNode(Attr attr);

    @Nullable
    @CEReactions
    Attr setAttributeNodeNS(Attr attr);

    @CEReactions
    Attr removeAttributeNode(Attr attr);

    ShadowRoot attachShadow(ShadowRootInit init);

    @Nullable
    @ReadonlyAttribute
    ShadowRoot shadowRoot();

    @Nullable
    Element closest(DOMString selectors);

    boolean matches(DOMString selectors);

    boolean webkitMatchesSelector(DOMString selectors); // historical alias of .matches

    HTMLCollection getElementsByTagName(DOMString qualifiedName);

    HTMLCollection getElementsByTagNameNS(@Nullable DOMString namespace, DOMString localName);

    HTMLCollection getElementsByClassName(DOMString classNames);

    @CEReactions
    @Nullable
    Element insertAdjacentElement(DOMString where, Element element); // historical

    void insertAdjacentText(DOMString where, DOMString data); // historical

    // std attributes

    Attribute<String> accesskey();

    Attribute<String> autocapitalize();

    Attribute<String> contenteditable();

    Attribute<String> dir();

    Attribute<String> draggable();

    Attribute<String> hidden();

    Attribute<String> inputmode();

    Attribute<String> is();

    Attribute<String> itemid();

    Attribute<String> itemprop();

    Attribute<String> itemref();

    Attribute<String> itemscope();

    Attribute<String> itemtype();

    Attribute<String> lang();

    Attribute<String> nonce();

    Attribute<String> spellcheck();

    Attribute<CSSStyleAttribute> style();

    Attribute<String> tabindex();

    Attribute<String> title();

    Attribute<String> translate();

    // region  https://w3c.github.io/DOM-Parsing

    @CEReactions
    @TreatNullAs(NullTreat.EmptyString)
    Attribute<DOMString> innerHTML();

    @CEReactions
    @TreatNullAs(NullTreat.EmptyString)
    Attribute<DOMString> outerHTML();

    @CEReactions
    void insertAdjacentHTML(DOMString position, DOMString text);
    
    // endregion
}
