package org.xhtmlrenderer.js.html5.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.js.html5.*;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.USVString;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.EventHandler;
import org.xhtmlrenderer.js.whatwg_dom.HTMLCollection;
import org.xhtmlrenderer.js.whatwg_dom.NodeList;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/17/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentImpl extends org.xhtmlrenderer.js.impl.DocumentImpl implements Document {
    
    Location location;
    
    public DocumentImpl(XHTMLPanel panel) {
        super(panel);
        location = new LocationImpl(panel);
    }

    @Override
    public Location location() {
        return location;
    }

    @Override
    public Attribute<USVString> domain() {
        return null;
    }

    @Override
    public USVString referrer() {
        return null;
    }

    @Override
    public Attribute<USVString> cookie() {
        return null;
    }

    @Override
    public DOMString lastModified() {
        return null;
    }

    @Override
    public DocumentReadyState readyState() {
        return null;
    }

    @Override
    public Object object(DOMString name) {
        return null;
    }

    @Override
    public Attribute<DOMString> title() {
        return null;
    }

    @Override
    public Attribute<DOMString> dir() {
        return null;
    }

    @Override
    public Attribute<HTMLElement> body() {
        return null;
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
    public NodeList getElementsByName(DOMString elementName) {
        return null;
    }

    @Override
    public HTMLOrSVGScriptElement currentScript() {
        return null;
    }

    @Override
    public Document open(DOMString type, DOMString replace) {
        return null;
    }

    @Override
    public WindowProxy open(USVString url, DOMString name, DOMString features) {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void write(DOMString... text) {

    }

    @Override
    public void writeln(DOMString... text) {

    }

    @Override
    public WindowProxy defaultView() {
        return null;
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
    public Attribute<DOMString> designMode() {
        return null;
    }

    @Override
    public boolean execCommand(DOMString commandId, boolean showUI, DOMString value) {
        return false;
    }

    @Override
    public boolean queryCommandEnabled(DOMString commandId) {
        return false;
    }

    @Override
    public boolean queryCommandIndeterm(DOMString commandId) {
        return false;
    }

    @Override
    public boolean queryCommandState(DOMString commandId) {
        return false;
    }

    @Override
    public boolean queryCommandSupported(DOMString commandId) {
        return false;
    }

    @Override
    public DOMString queryCommandValue(DOMString commandId) {
        return null;
    }

    @Override
    public Attribute<EventHandler> onreadystatechange() {
        return null;
    }
}
