package com.earnix.webk.script.html.impl;

import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.script.Binder;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.html.DocumentReadyState;
import com.earnix.webk.script.html.HTMLElement;
import com.earnix.webk.script.html.HTMLHeadElement;
import com.earnix.webk.script.html.HTMLOrSVGScriptElement;
import com.earnix.webk.script.html.Location;
import com.earnix.webk.script.html.WindowProxy;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.HTMLCollection;
import com.earnix.webk.script.whatwg_dom.NodeList;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentImpl extends com.earnix.webk.script.impl.DocumentImpl implements com.earnix.webk.script.html.Document {

    Location location;
    DocumentModel document;


    public DocumentImpl(ScriptContext ctx) {
        super(ctx);
        location = new LocationImpl(panel);
        document = panel.getDocument();
    }

    @Override
    public Location location() {
        return location;
    }

    @Override
    @USVString
    public Attribute<String> domain() {
        return null;
    }

    @Override
    public String referrer() {
        return null;
    }

    @Override
    public Attribute<String> cookie() {
        return null;
    }

    @Override
    public String lastModified() {
        return null;
    }

    @Override
    public DocumentReadyState readyState() {
        return null;
    }

    @Override
    public Object object(String name) {
        return null;
    }

    @Override
    public Attribute<String> title() {
        return null;
    }

    @Override
    public Attribute<String> dir() {
        return null;
    }

    @Override
    public Attribute<HTMLElement> body() {
        return new Attribute<HTMLElement>() {
            @Override
            public HTMLElement get() {
                val bodyModel = document.getElementsByTag("body");
                if (bodyModel.isEmpty()) {
                    return null;
                }
                return (HTMLElement) Binder.getElement(bodyModel.get(0), panel);
            }

            @Override
            public void set(HTMLElement htmlElement) {
                val bodyModel = document.getElementsByTag("body");
                if (!bodyModel.isEmpty()) {
                    bodyModel.forEach(NodeModel::remove);
                }
                document.appendChild(((ElementImpl) htmlElement).getModel());
            }
        };
    }

    @Override
    public HTMLHeadElement head() {
        return null;
    }

    @Override
    public HTMLCollection images() {
        return null;
    }

    @Override
    public HTMLCollection embeds() {
        return null;
    }

    @Override
    public HTMLCollection plugins() {
        return null;
    }

    @Override
    public HTMLCollection links() {
        return null;
    }

    @Override
    public HTMLCollection forms() {
        return null;
    }

    @Override
    public HTMLCollection scripts() {
        return null;
    }

    @Override
    public NodeList getElementsByName(@DOMString String elementName) {
        return null;
    }

    @Override
    public HTMLOrSVGScriptElement currentScript() {
        return null;
    }

    @Override
    public com.earnix.webk.script.html.Document open(@DOMString String type, @DOMString String replace) {
        return null;
    }

    @Override
    public WindowProxy open(@USVString String url, @DOMString String name, @DOMString String features) {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void write(@DOMString String... text) {

    }

    @Override
    public void writeln(@DOMString String... text) {

    }

    @Override
    public WindowProxy defaultView() {
        return new WindowProxyImpl(ctx.getWindow());
    }

    @Override
    public Element activeElement() {
        return null;
    }

    @Override
    public boolean hasFocus() {
        return false;
    }

    @Override
    public Attribute<String> designMode() {
        return null;
    }

    @Override
    public boolean execCommand(@DOMString String commandId, boolean showUI, @DOMString String value) {
        return false;
    }

    @Override
    public boolean queryCommandEnabled(@DOMString String commandId) {
        return false;
    }

    @Override
    public boolean queryCommandIndeterm(@DOMString String commandId) {
        return false;
    }

    @Override
    public boolean queryCommandState(@DOMString String commandId) {
        return false;
    }

    @Override
    public boolean queryCommandSupported(@DOMString String commandId) {
        return false;
    }

    @Override
    public @DOMString String queryCommandValue(@DOMString String commandId) {
        return null;
    }

    @Override
    public Attribute<EventHandler> onreadystatechange() {
        return null;
    }
}
