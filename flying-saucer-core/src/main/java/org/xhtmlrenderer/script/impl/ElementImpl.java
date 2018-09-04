package org.xhtmlrenderer.script.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.xhtmlrenderer.script.Binder;
import org.xhtmlrenderer.script.cssom_view.ScrollToOptions;
import org.xhtmlrenderer.script.geom.DOMRect;
import org.xhtmlrenderer.script.geom.DOMRectList;
import org.xhtmlrenderer.script.html5.HTMLElement;
import org.xhtmlrenderer.script.html5.canvas.HTMLSlotElement;
import org.xhtmlrenderer.script.web_idl.Attribute;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.Sequence;
import org.xhtmlrenderer.script.whatwg_dom.*;
import org.xhtmlrenderer.script.whatwg_dom.css_style_attribute.CSSStyleAttribute;
import org.xhtmlrenderer.script.whatwg_dom.impl.HTMLCollectionImpl;
import org.xhtmlrenderer.script.whatwg_dom.impl.NamedNodeMapImpl;
import org.xhtmlrenderer.swing.BasicPanel;

import java.util.HashSet;
/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ElementImpl extends NodeImpl implements HTMLElement {
    
    final org.xhtmlrenderer.dom.nodes.Element model;
    final ChildNodeImpl childNodeMixin;
    final BasicPanel panel;

    public ElementImpl(org.xhtmlrenderer.dom.nodes.Element model, BasicPanel panel) {
        super(model, panel);
        this.model = model;
        this.panel = panel;
        childNodeMixin = new ChildNodeImpl(model);
    }

    public org.xhtmlrenderer.dom.nodes.Element getModel() {
        return model;
    }

    @Override
    public @DOMString String namespaceURI() {
        return null;
    }

    @Override
    public @DOMString String prefix() {
        return null;
    }

    @Override
    public @DOMString String localName() {
        return null;
    }

    @Override
    public @DOMString String tagName() {
        return model.nodeName();
    }

    @Override
    public Attribute<String> id() {
        return Attribute.forNode(model, "id");
    }

    @Override
    public Attribute<String> className() {
        return Attribute.forNode(model, "className");
    }

    @Override
    public DOMTokenList classList() {
        return new DOMTokenListImpl(model.classNames(), strings -> model.classNames(new HashSet<>(strings)));
    }

    @Override
    public Attribute<String> slot() {
        return null;
    }

    @Override
    public boolean hasAttributes() {
        return model.attributes().size() > 0;
    }

    @Override
    public NamedNodeMap attributes() {
        return new NamedNodeMapImpl(model, panel);
    }

    @Override
    public Sequence<String> getAttributeNames() {
        return null;
    }

    @Override
    public @DOMString String getAttribute(@DOMString String qualifiedName) {
        return null;
    }

    @Override
    public @DOMString String getAttributeNS(@DOMString String namespace, @DOMString String localName) {
        return null;
    }

    @Override
    public void setAttribute(@DOMString String qualifiedName, @DOMString String value) {
        model.attr(qualifiedName, value);
    }

    @Override
    public void setAttributeNS(@DOMString String namespace, @DOMString String qualifiedName, @DOMString String value) {
    }

    @Override
    public void removeAttribute(@DOMString String qualifiedName) {
        model.removeAttr(qualifiedName);
    }

    @Override
    public void removeAttributeNS(@DOMString String namespace, @DOMString String localName) {

    }

    @Override
    public boolean toggleAttribute(@DOMString String qualifiedName, Boolean force) {
        if(model.hasAttr(qualifiedName)) {
            model.removeAttr(qualifiedName);
            return true;
        } else {
            model.attr(qualifiedName, "");
            return true;
        }
    }

    @Override
    public boolean hasAttribute(@DOMString String qualifiedName) {
        return model.hasAttr(qualifiedName);
    }

    @Override
    public boolean hasAttributeNS(@DOMString String  namespace, @DOMString String localName) {
        return false;
    }

    @Override
    public Attr getAttributeNode(@DOMString String qualifiedName) {
        return null;
    }

    @Override
    public Attr getAttributeNodeNS(@DOMString String namespace, @DOMString String localName) {
        return null;
    }

    @Override
    public Attr setAttributeNode(Attr attr) {
        return null;
    }

    @Override
    public Attr setAttributeNodeNS(Attr attr) {
        return null;
    }

    @Override
    public Attr removeAttributeNode(Attr attr) {
        return null;
    }

    @Override
    public ShadowRoot attachShadow(ShadowRootInit init) {
        return null;
    }

    @Override
    public ShadowRoot shadowRoot() {
        return null;
    }

    @Override
    public Element closest(@DOMString String selectors) {
        return null;
    }

    @Override
    public boolean matches(@DOMString String selectors) {
        return false;
    }

    @Override
    public boolean webkitMatchesSelector(@DOMString String selectors) {
        return false;
    }

    @Override
    public HTMLCollection getElementsByTagName(@DOMString String qualifiedName) {
        return new HTMLCollectionImpl(model.getElementsByTag(qualifiedName.toString()), panel);
    }

    @Override
    public HTMLCollection getElementsByTagNameNS(@DOMString String namespace, @DOMString String localName) {
        return getElementsByTagName(localName);
    }

    @Override
    public HTMLCollection getElementsByClassName(@DOMString String classNames) {
        return new HTMLCollectionImpl(model.getElementsByClass(classNames), panel);
    }

    @Override
    public Element insertAdjacentElement(@DOMString String where, Element element) {
        return null;
    }

    @Override
    public void insertAdjacentText(@DOMString String where, @DOMString String data) {

    }

    // region ChildNode
    
    @Override
    public void before(Object... nodes) {
        childNodeMixin.before(nodes);
    }

    @Override
    public void after(Object... nodes) {
        childNodeMixin.after(nodes);
    }

    @Override
    public void replaceWith(Object... nodes) {
        childNodeMixin.replaceWith(nodes);
    }

    @Override
    public void remove() {
        childNodeMixin.remove();
    }
    
    // endregion

    @Override
    public Element previousElementSibling() {
        return Binder.getElement(model.previousElementSibling(), panel);
    }

    @Override
    public Element nextElementSibling() {
        return Binder.getElement(model.nextElementSibling(), panel);
    }

    @Override
    public HTMLCollection children() {
        return new HTMLCollectionImpl(model.children(), panel);
    }

    @Override
    public Element firstElementChild() {
        return null;
    }

    @Override
    public Element lastElementChild() {
        return null;
    }

    @Override
    public Integer childElementCount() {
        return model.children().size();
    }

    @Override
    public void prepend(Object... nodes) {

    }

    @Override
    public void append(Object... nodes) {

    }

    @Override
    public Element querySelector(@DOMString String selectors) {
        return null;
    }

    @Override
    public NodeList querySelectorAll(@DOMString String selectors) {
        return null;
    }

    @Override
    public HTMLSlotElement assignedSlot() {
        return null;
    }
    
    // common attributes https://html.spec.whatwg.org/multipage/dom.html#global-attributes

    @Override
    public Attribute<String> accesskey() {
        return bindAttribute("accesskey");
    }

    @Override
    public Attribute<String> autocapitalize() {
        return bindAttribute("autocapitalize");
    }

    @Override
    public Attribute<String> contenteditable() {
        return bindAttribute("contenteditable");
    }

    @Override
    public Attribute<String> dir() {
        return bindAttribute("dir");
    }

    @Override
    public Attribute<String> draggable() {
        return bindAttribute("draggable");
    }

    @Override
    public Attribute<String> hidden() {
        return bindAttribute("hidden");
    }

    @Override
    public Attribute<String> inputmode() {
        return bindAttribute("inputmode");
    }

    @Override
    public Attribute<String> is() {
        return bindAttribute("is");
    }

    @Override
    public Attribute<String> itemid() {
        return bindAttribute("itemid");
    }

    @Override
    public Attribute<String> itemprop() {
        return bindAttribute("itemprop");
    }

    @Override
    public Attribute<String> itemref() {
        return bindAttribute("itemref");
    }

    @Override
    public Attribute<String> itemscope() {
        return bindAttribute("itemscope");
    }

    @Override
    public Attribute<String> itemtype() {
        return bindAttribute("itemtype");
    }

    @Override
    public Attribute<String> lang() {
        return bindAttribute("lang");
    }

    @Override
    public Attribute<String> nonce() {
        return bindAttribute("nonce");
    }

    @Override
    public Attribute<String> spellcheck() {
        return bindAttribute("spellcheck");
    }

    @Override
    public Attribute<CSSStyleAttribute> style() {
        return Attribute.<CSSStyleAttribute>receive((a) -> model.attr("style", a.toCSSString()))
                .give(() -> new CSSStyleAttribute(model, panel));
    }

    @Override
    public Attribute<String> tabindex() {
        return bindAttribute("tabindex");
    }

    @Override
    public Attribute<String> title() {
        return bindAttribute("title");
    }

    @Override
    public Attribute<String> translate() {
        return bindAttribute("translate");
    }
    
    private Attribute<String> bindAttribute(String name){
        return Attribute.<String>receive((s) -> model.attr(name, s)).give(() -> model.attr(name));
    }

    @Override
    public Attribute<String> innerHTML() {
        
        return new Attribute<String>() {
            @Override
            public String get() {
                return model.html();
            }

            @Override
            public void set(String string) {
                model.outerHtml();
                model.html(string);
            }
        };
        
    }

    @Override
    public Attribute<String> outerHTML() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return model.outerHtml();
            }

            @Override
            public void set(String string) {
                log.warn("outerHTML unimplemented");
            }
        };
    }

    @Override
    public void insertAdjacentHTML(@DOMString String position, @DOMString String text) {

    }
    
    // region CSSOM-view

    @Override
    public DOMRectList getClientRects() {
        return null;
    }

    @Override
    public DOMRect getBoundingClientRect() {
        return null;
    }

    @Override
    public void scrollIntoView(Object arg) {

    }

    @Override
    public void scroll(ScrollToOptions options) {

    }

    @Override
    public void scroll(double x, double y) {

    }

    @Override
    public void scrollTo(ScrollToOptions options) {

    }

    @Override
    public void scrollTo(double x, double y) {

    }

    @Override
    public void scrollBy(ScrollToOptions options) {

    }

    @Override
    public void scrollBy(double x, double y) {

    }

    @Override
    public Attribute<Double> scrollTop() {
        return null;
    }

    @Override
    public Attribute<Double> scrollLeft() {
        return null;
    }

    @Override
    public int scrollWidth() {
        return 0;
    }

    @Override
    public int scrollHeight() {
        return 0;
    }

    @Override
    public int clientTop() {
        return 0;
    }

    @Override
    public int clientLeft() {
        return 0;
    }
    
    @Override
    public int clientWidth() {
        val box = model.getView();
        if (box != null) {
            val paddingStyle = box.getPadding(panel.getLayoutContext());
            return  (int) (box.getContentWidth() + paddingStyle.left() + paddingStyle.right());
        } else {
            return 0;
        }
    }

    @Override
    public int clientHeight() {
        return 0;
    }


    // endregion
}
