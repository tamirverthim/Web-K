package org.xhtmlrenderer.script.html5.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.dom.nodes.Node;
import org.xhtmlrenderer.script.Binder;
import org.xhtmlrenderer.script.html5.*;
import org.xhtmlrenderer.script.impl.ElementImpl;
import org.xhtmlrenderer.script.web_idl.Attribute;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.web_idl.USVString;
import org.xhtmlrenderer.script.whatwg_dom.Element;
import org.xhtmlrenderer.script.whatwg_dom.EventHandler;
import org.xhtmlrenderer.script.whatwg_dom.HTMLCollection;
import org.xhtmlrenderer.script.whatwg_dom.NodeList;
import org.xhtmlrenderer.swing.BasicPanel;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentImpl extends org.xhtmlrenderer.script.impl.DocumentImpl implements Document {
    
    Location location;
    org.xhtmlrenderer.dom.nodes.Document document;
    
    
    public DocumentImpl(BasicPanel panel) {
        super(panel);
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
                if(bodyModel.isEmpty()){
                    return null;
                }
                return (HTMLElement) Binder.getElement(bodyModel.get(0), panel);
            }

            @Override
            public void set(HTMLElement htmlElement) {
                val bodyModel = document.getElementsByTag("body");
                if(!bodyModel.isEmpty()){
                    bodyModel.forEach(Node::remove);
                }
                document.appendChild(((ElementImpl)htmlElement).getModel());
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
    public Document open(@DOMString String type, @DOMString String replace) {
        return null;
    }

    @Override
    public @WindowProxy Object open(@USVString String url, @DOMString String name, @DOMString String features) {
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
    public @WindowProxy Object defaultView() {
        return panel.getScriptContext().getWindow();
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
