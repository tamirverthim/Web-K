package org.xhtmlrenderer.js.whatwg_dom.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.TextNode;
import org.xhtmlrenderer.js.html5.canvas.HTMLSlotElement;
import org.xhtmlrenderer.js.impl.*;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.Text;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/24/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextImpl extends NodeImpl implements Text {

    ChildNodeImpl childNodeMixin;
    NonDocumentTypeChildNodeImpl nonDocumentTypeChildNodeMixin;
    TextNode target;

    // text

    
    

    public TextImpl(TextNode target, XHTMLPanel panel) {
        super(target, panel);
        this.target = target;
        this.childNodeMixin = new ChildNodeImpl(target);
        this.nonDocumentTypeChildNodeMixin  = new NonDocumentTypeChildNodeImpl(target, panel);
    }

    @Override
    public void construct(DOMString data) {
        
    }

    @Override
    public Text splitText(long offset) {
        return null;
    }

    @Override
    public DOMString wholeText() {
        return null;
    }

    // cdata
    
    @Override
    public Attribute<DOMString> data() {
        return null;
    }

    @Override
    public long length() {
        return 0;
    }

    @Override
    public DOMString substringData(int offset, int count) {
        return null;
    }

    @Override
    public void appendData(DOMString data) {

    }

    @Override
    public void insertData(int offset, DOMString data) {

    }

    @Override
    public void deleteData(int offset, int count) {

    }

    @Override
    public void replaceData(int offset, int count, DOMString data) {

    }
    
    // child node

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
    
    // slotable


    @Override
    public HTMLSlotElement assignedSlot() {
        return null;
    }
    
    // non doc type child node


    @Override
    public Element previousElementSibling() {
        return nonDocumentTypeChildNodeMixin.previousElementSibling();
    }

    @Override
    public Element nextElementSibling() {
        return nonDocumentTypeChildNodeMixin.nextElementSibling();
    }
}
