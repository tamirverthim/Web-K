package com.earnix.webk.script.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.cssom.CSSStyleDeclaration;
import com.earnix.webk.script.cssom.impl.CSSStyleDeclarationImpl;
import com.earnix.webk.script.cssom_view.ScrollToOptions;
import com.earnix.webk.script.geom.DOMRect;
import com.earnix.webk.script.geom.DOMRectList;
import com.earnix.webk.script.geom.impl.DOMRectImpl;
import com.earnix.webk.script.html.HTMLElement;
import com.earnix.webk.script.html.canvas.HTMLSlotElement;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Sequence;
import com.earnix.webk.script.whatwg_dom.Attr;
import com.earnix.webk.script.whatwg_dom.DOMTokenList;
import com.earnix.webk.script.whatwg_dom.DOMTokenListImpl;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.HTMLCollection;
import com.earnix.webk.script.whatwg_dom.NamedNodeMap;
import com.earnix.webk.script.whatwg_dom.Node;
import com.earnix.webk.script.whatwg_dom.NodeList;
import com.earnix.webk.script.whatwg_dom.ShadowRoot;
import com.earnix.webk.script.whatwg_dom.ShadowRootInit;
import com.earnix.webk.script.whatwg_dom.impl.HTMLCollectionImpl;
import com.earnix.webk.script.whatwg_dom.impl.NamedNodeMapImpl;
import com.earnix.webk.script.whatwg_dom.impl.ScriptDOMFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.HashSet;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ElementImpl extends NodeImpl implements HTMLElement {

    final ElementModel model;
    final ChildNodeImpl childNodeMixin;
    final ScriptContext scriptContext;

    public ElementImpl(ScriptContext scriptContext, ElementModel model) {
        super(scriptContext, model);
        this.model = model;
        this.scriptContext = scriptContext;
        childNodeMixin = new ChildNodeImpl(model);
    }

    public ElementModel getModel() {
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
        return new NamedNodeMapImpl(model, this.scriptContext);
    }

    @Override
    public Sequence<String> getAttributeNames() {
        return null;
    }

    @Override
    public @DOMString String getAttribute(@DOMString String qualifiedName) {
        return model.attr(qualifiedName);
    }

    @Override
    public @DOMString String getAttributeNS(@DOMString String namespace, @DOMString String localName) {
        return model.attr(localName);
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
        if (model.hasAttr(qualifiedName)) {
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
    public boolean hasAttributeNS(@DOMString String namespace, @DOMString String localName) {
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
        return new HTMLCollectionImpl(model.getElementsByTag(qualifiedName));
    }

    @Override
    public HTMLCollection getElementsByTagNameNS(@DOMString String namespace, @DOMString String localName) {
        return getElementsByTagName(localName);
    }

    @Override
    public HTMLCollection getElementsByClassName(@DOMString String classNames) {
        return new HTMLCollectionImpl(model.getElementsByClass(classNames));
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
        return ScriptDOMFactory.getElement(scriptContext, model.previousElementSibling());
    }

    @Override
    public Element nextElementSibling() {
        return ScriptDOMFactory.getElement(scriptContext, model.nextElementSibling());
    }

    @Override
    public HTMLCollection children() {
        return new HTMLCollectionImpl(model.children());
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
        for (Object node : nodes) {
            if (node instanceof String) {
                model.prepend((String)node);
            } else {
                if (node instanceof Node) {
                    NodeImpl impl = (NodeImpl) node;
                    model.prependChild(impl.getModel());
                }
            }
        }
    }

    @Override
    public void append(Object... nodes) {
        for (Object node : nodes) {
            if (node instanceof String) {
                model.append((String)node);
            } else {
                if (node instanceof Node) {
                    NodeImpl impl = (NodeImpl) node;
                    model.appendChild(impl.getModel());
                }
            }
        }
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
    public CSSStyleDeclaration style() {
        return new CSSStyleDeclarationImpl(model, this.scriptContext);
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

    private Attribute<String> bindAttribute(String name) {
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
        val view = model.getView();
        val x = view.getX();
        val y = view.getY();
        val paddingStyle = view.getPadding(this.scriptContext.getPanel().getLayoutContext());
        val width = view.getContentWidth() + paddingStyle.left() + paddingStyle.right();
        val height = view.getHeight() + paddingStyle.top() + paddingStyle.bottom();
        return new DOMRectImpl(x, y, width, height);
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
            val paddingStyle = box.getPadding(this.scriptContext.getPanel().getLayoutContext());
            return (int) (box.getContentWidth() + paddingStyle.left() + paddingStyle.right());
        } else {
            return 0;
        }
    }

    @Override
    public int clientHeight() {
        val view = model.getView();
        val paddingStyle = view.getPadding(this.scriptContext.getPanel().getLayoutContext());
        return (int) (view.getHeight() + paddingStyle.top() + paddingStyle.bottom());
    }

    @Override
    public Attribute<EventHandler> onabort() {
        return level1EventTarget.getHandlerAttribute("onabort");
    }

    @Override
    public Attribute<EventHandler> onauxclick() {
        return level1EventTarget.getHandlerAttribute("onauxclick");
    }

    @Override
    public Attribute<EventHandler> onblur() {
        return level1EventTarget.getHandlerAttribute("onblur");
    }

    @Override
    public Attribute<EventHandler> oncancel() {
        return level1EventTarget.getHandlerAttribute("oncancel");
    }

    @Override
    public Attribute<EventHandler> oncanplay() {
        return level1EventTarget.getHandlerAttribute("oncanplay");
    }

    @Override
    public Attribute<EventHandler> oncanplaythrough() {
        return level1EventTarget.getHandlerAttribute("oncanplaythrough");
    }

    @Override
    public Attribute<EventHandler> onchange() {
        return level1EventTarget.getHandlerAttribute("onchange");
    }

    @Override
    public Attribute<EventHandler> onclick() {
        return level1EventTarget.getHandlerAttribute("onclick");
    }

    @Override
    public Attribute<EventHandler> onclose() {
        return level1EventTarget.getHandlerAttribute("onclose");
    }

    @Override
    public Attribute<EventHandler> oncontextmenu() {
        return level1EventTarget.getHandlerAttribute("oncontextmenu");
    }

    @Override
    public Attribute<EventHandler> oncuechange() {
        return level1EventTarget.getHandlerAttribute("oncuechange");
    }

    @Override
    public Attribute<EventHandler> ondblclick() {
        return level1EventTarget.getHandlerAttribute("ondblclick");
    }

    @Override
    public Attribute<EventHandler> ondrag() {
        return level1EventTarget.getHandlerAttribute("ondrag");
    }

    @Override
    public Attribute<EventHandler> ondragend() {
        return level1EventTarget.getHandlerAttribute("ondragend");
    }

    @Override
    public Attribute<EventHandler> ondragenter() {
        return level1EventTarget.getHandlerAttribute("ondragenter");
    }

    @Override
    public Attribute<EventHandler> ondragexit() {
        return level1EventTarget.getHandlerAttribute("ondragexit");
    }

    @Override
    public Attribute<EventHandler> ondragleave() {
        return level1EventTarget.getHandlerAttribute("ondragleave");
    }

    @Override
    public Attribute<EventHandler> ondragover() {
        return level1EventTarget.getHandlerAttribute("ondragover");
    }

    @Override
    public Attribute<EventHandler> ondragstart() {
        return level1EventTarget.getHandlerAttribute("ondragstart");
    }

    @Override
    public Attribute<EventHandler> ondrop() {
        return level1EventTarget.getHandlerAttribute("ondrop");
    }

    @Override
    public Attribute<EventHandler> ondurationchange() {
        return level1EventTarget.getHandlerAttribute("ondurationchange");
    }

    @Override
    public Attribute<EventHandler> onemptied() {
        return level1EventTarget.getHandlerAttribute("onemptied");
    }

    @Override
    public Attribute<EventHandler> onended() {
        return level1EventTarget.getHandlerAttribute("onended");
    }

    @Override
    public Attribute<EventHandler> onfocus() {
        return level1EventTarget.getHandlerAttribute("onfocus");
    }

    @Override
    public Attribute<EventHandler> oninput() {
        return level1EventTarget.getHandlerAttribute("oninput");
    }

    @Override
    public Attribute<EventHandler> oninvalid() {
        return level1EventTarget.getHandlerAttribute("oninvalid");
    }

    @Override
    public Attribute<EventHandler> onkeydown() {
        return level1EventTarget.getHandlerAttribute("onkeydown");
    }

    @Override
    public Attribute<EventHandler> onkeypress() {
        return level1EventTarget.getHandlerAttribute("onkeypress");
    }

    @Override
    public Attribute<EventHandler> onkeyup() {
        return level1EventTarget.getHandlerAttribute("onkeyup");
    }

    @Override
    public Attribute<EventHandler> onload() {
        return level1EventTarget.getHandlerAttribute("onload");
    }

    @Override
    public Attribute<EventHandler> onloadeddata() {
        return level1EventTarget.getHandlerAttribute("onloadeddata");
    }

    @Override
    public Attribute<EventHandler> onloadedmetadata() {
        return level1EventTarget.getHandlerAttribute("onloadedmetadata");
    }

    @Override
    public Attribute<EventHandler> onloadend() {
        return level1EventTarget.getHandlerAttribute("onloadend");
    }

    @Override
    public Attribute<EventHandler> onloadstart() {
        return level1EventTarget.getHandlerAttribute("onloadstart");
    }

    @Override
    public Attribute<EventHandler> onmousedown() {
        return level1EventTarget.getHandlerAttribute("onmousedown");
    }

    @Override
    public Attribute<EventHandler> onmouseenter() {
        return level1EventTarget.getHandlerAttribute("onmouseenter");
    }

    @Override
    public Attribute<EventHandler> onmouseleave() {
        return level1EventTarget.getHandlerAttribute("onmouseleave");
    }

    @Override
    public Attribute<EventHandler> onmousemove() {
        return level1EventTarget.getHandlerAttribute("onmousemove");
    }

    @Override
    public Attribute<EventHandler> onmouseout() {
        return level1EventTarget.getHandlerAttribute("onmouseout");
    }

    @Override
    public Attribute<EventHandler> onmouseover() {
        return level1EventTarget.getHandlerAttribute("onmouseover");
    }

    @Override
    public Attribute<EventHandler> onmouseup() {
        return level1EventTarget.getHandlerAttribute("onmouseup");
    }

    @Override
    public Attribute<EventHandler> onwheel() {
        return level1EventTarget.getHandlerAttribute("onwheel");
    }

    @Override
    public Attribute<EventHandler> onpause() {
        return level1EventTarget.getHandlerAttribute("onpause");
    }

    @Override
    public Attribute<EventHandler> onplay() {
        return level1EventTarget.getHandlerAttribute("onplay");
    }

    @Override
    public Attribute<EventHandler> onplaying() {
        return level1EventTarget.getHandlerAttribute("onplaying");
    }

    @Override
    public Attribute<EventHandler> onprogress() {
        return level1EventTarget.getHandlerAttribute("onprogress");
    }

    @Override
    public Attribute<EventHandler> onratechange() {
        return level1EventTarget.getHandlerAttribute("onratechange");
    }

    @Override
    public Attribute<EventHandler> onreset() {
        return level1EventTarget.getHandlerAttribute("onreset");
    }

    @Override
    public Attribute<EventHandler> onresize() {
        return level1EventTarget.getHandlerAttribute("onresize");
    }

    @Override
    public Attribute<EventHandler> onscroll() {
        return level1EventTarget.getHandlerAttribute("onscroll");
    }

    @Override
    public Attribute<EventHandler> onsecuritypolicyviolation() {
        return level1EventTarget.getHandlerAttribute("onsecuritypolicyviolation");
    }

    @Override
    public Attribute<EventHandler> onseeked() {
        return level1EventTarget.getHandlerAttribute("onseeked");
    }

    @Override
    public Attribute<EventHandler> onseeking() {
        return level1EventTarget.getHandlerAttribute("onseeking");
    }

    @Override
    public Attribute<EventHandler> onselect() {
        return level1EventTarget.getHandlerAttribute("onselect");
    }

    @Override
    public Attribute<EventHandler> onstalled() {
        return level1EventTarget.getHandlerAttribute("onstalled");
    }

    @Override
    public Attribute<EventHandler> onsubmit() {
        return level1EventTarget.getHandlerAttribute("onsubmit");
    }

    @Override
    public Attribute<EventHandler> onsuspend() {
        return level1EventTarget.getHandlerAttribute("onsuspend");
    }

    @Override
    public Attribute<EventHandler> ontimeupdate() {
        return level1EventTarget.getHandlerAttribute("ontimeupdate");
    }

    @Override
    public Attribute<EventHandler> ontoggle() {
        return level1EventTarget.getHandlerAttribute("ontoggle");
    }

    @Override
    public Attribute<EventHandler> onvolumechange() {
        return level1EventTarget.getHandlerAttribute("onvolumechange");
    }

    @Override
    public Attribute<EventHandler> onwaiting() {
        return level1EventTarget.getHandlerAttribute("onwaiting");
    }

    @Override
    public String toString() {
        return model.toString();
    }

    // endregion


    @Override
    public Attribute<String> textContent() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return model.wholeText();
            }

            @Override
            public void set(String s) {
                if (s != null) {
                    model.text(s);
                } else {
                    model.text("");
                }
            }
        };
    }
}
