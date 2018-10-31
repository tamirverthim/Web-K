package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.TextNodeModel;
import com.earnix.webk.script.html.canvas.HTMLSlotElement;
import com.earnix.webk.script.impl.ChildNodeImpl;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.script.impl.NonDocumentTypeChildNodeImpl;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.Text;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * @author Taras Maslov
 * 7/24/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextImpl extends NodeImpl implements Text {

    ChildNodeImpl childNodeMixin;
    NonDocumentTypeChildNodeImpl nonDocumentTypeChildNodeMixin;
    TextNodeModel target;

    // text

    public TextImpl(TextNodeModel target, BasicPanel panel) {
        super(target, panel);
        this.target = target;
        this.childNodeMixin = new ChildNodeImpl(target);
        this.nonDocumentTypeChildNodeMixin = new NonDocumentTypeChildNodeImpl(target, panel);
    }

    @Override
    public void construct(@DOMString String data) {

    }

    @Override
    public Text splitText(long offset) {
        return null;
    }

    @Override
    public String wholeText() {
        return target.text();
    }

    // cdata

    @Override
    public Attribute<String> data() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return target.getWholeText();
            }

            @Override
            public void set(String s) {
                target.text(s);
            }
        };
    }

    @Override
    public long length() {
        return target.getWholeText().length();
    }

    @Override
    public String substringData(int offset, int count) {
        try {
            return target.getWholeText().substring(offset, offset + count + 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("Range error");
        }
    }

    @Override
    public void appendData(String data) {
        target.text(target.getWholeText() + data);
    }

    @Override
    public void insertData(int offset, String data) {
        val sb = new StringBuilder(target.getWholeText());
        sb.insert(offset, data);
        target.text(sb.toString());
    }

    @Override
    public void deleteData(int offset, int count) {
        val sb = new StringBuilder(target.getWholeText());
        sb.delete(offset, sb.length() - count);
        target.text(sb.toString());
    }

    @Override
    public void replaceData(int offset, int count, String data) {
        val sb = new StringBuilder(target.getWholeText());
        sb.replace(offset, sb.length() - count, data);
        target.text(sb.toString());
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
