package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;
import org.xhtmlrenderer.script.whatwg_dom.css_style_attribute.CSSStyleAttribute;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
public interface Element extends Node, ParentNode, NonDocumentTypeChildNode, ChildNode, Slotable, org.xhtmlrenderer.script.cssom_view.Element {
    
    @ReadonlyAttribute
    @Nullable
    @DOMString String namespaceURI();

    @ReadonlyAttribute
    @Nullable
    @DOMString String prefix();

    @ReadonlyAttribute
    @DOMString
    String localName();

    @ReadonlyAttribute
    @DOMString
    String tagName();

    @CEReactions
    @DOMString
    Attribute<String> id();

    @CEReactions
    @DOMString
    Attribute<String> className();

    @SameObject
    @PutForwards("value")
    @ReadonlyAttribute
    DOMTokenList classList();

    @CEReactions
    @Unscopable
    @DOMString
    Attribute<String> slot();

    boolean hasAttributes();

    @SameObject
    @ReadonlyAttribute
    NamedNodeMap attributes();

    @DOMString
    Sequence<String> getAttributeNames();

    @Nullable
    @DOMString String getAttribute(@DOMString String qualifiedName);

    @Nullable
    @DOMString String getAttributeNS(@Nullable @DOMString String namespace, @DOMString String localName);

    @CEReactions
    void setAttribute(
            @DOMString String qualifiedName,
            @DOMString String value
    );

    @CEReactions
    void setAttributeNS(@Nullable @DOMString String namespace, @DOMString String qualifiedName, @DOMString String value);

    @CEReactions
    void removeAttribute(@DOMString String qualifiedName);

    @CEReactions
    void removeAttributeNS(@Nullable @DOMString String namespace, @DOMString String localName);

    @CEReactions
    boolean toggleAttribute(@DOMString String qualifiedName, @Optional Boolean force);

    boolean hasAttribute(@DOMString String qualifiedName);

    boolean hasAttributeNS(@Nullable @DOMString String namespace, @DOMString String localName);

    @Nullable
    Attr getAttributeNode(@DOMString String qualifiedName);

    @Nullable
    Attr getAttributeNodeNS(@Nullable @DOMString String namespace, @DOMString String localName);

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
    Element closest(@DOMString String selectors);

    boolean matches(@DOMString String selectors);

    boolean webkitMatchesSelector(@DOMString String selectors); // historical alias of .matches

    HTMLCollection getElementsByTagName(@DOMString String qualifiedName);

    HTMLCollection getElementsByTagNameNS(@Nullable @DOMString String namespace, @DOMString String localName);

    HTMLCollection getElementsByClassName(@DOMString String classNames);

    @CEReactions
    @Nullable
    Element insertAdjacentElement(@DOMString String where, Element element); // historical

    void insertAdjacentText(@DOMString String where, @DOMString String data); // historical

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
    @DOMString
    Attribute<String> innerHTML();

    @CEReactions
    @TreatNullAs(NullTreat.EmptyString)
    @DOMString
    Attribute<String> outerHTML();

    @CEReactions
    void insertAdjacentHTML(@DOMString String position, @DOMString String text);

    // endregion
}
