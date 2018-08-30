package org.xhtmlrenderer.script.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.script.web_idl.Attribute;
import org.xhtmlrenderer.script.web_idl.DOMException;
import org.xhtmlrenderer.script.web_idl.DOMString;
import org.xhtmlrenderer.script.whatwg_dom.Comment;
import org.xhtmlrenderer.script.whatwg_dom.Element;
import org.xhtmlrenderer.script.whatwg_dom.NonDocumentTypeChildNode;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/13/2018
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentImpl extends NodeImpl implements Comment {
    org.xhtmlrenderer.dom.nodes.Comment target;

    ChildNodeImpl childNodeMixin;
    NonDocumentTypeChildNode nonDocumentTypeChildNodeMixin;

    Attribute<String> dataAttribute = Attribute.<String>receive(val -> {
        val next = new org.xhtmlrenderer.dom.nodes.Comment(val);
        target.replaceWith(next);
//        Binder.remove(target);
    }).give(() -> target.getData());

    public CommentImpl(org.xhtmlrenderer.dom.nodes.Comment target, XHTMLPanel panel) {
        super(target, panel);
        this.target = target;
        childNodeMixin = new ChildNodeImpl(target);
        nonDocumentTypeChildNodeMixin = new NonDocumentTypeChildNodeImpl(target, panel);
    }


    @Override
    public Attribute<String> data() {
        return dataAttribute;
    }

    @Override
    public long length() {
        return target.getData().length();
    }

    @Override
    public String substringData(int offset, int count) {
        try {
            return target.getData().substring(offset, offset + count + 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void appendData(String data) {
        val next = new org.xhtmlrenderer.dom.nodes.Comment(target.getData() + data);
        target.replaceWith(next);
        target = next;
    }

    @Override
    public void insertData(int offset, String data) {
        StringBuilder stringBuilder = new StringBuilder(target.getData());
        try {
            stringBuilder.insert(offset, data);
            val next = new org.xhtmlrenderer.dom.nodes.Comment(stringBuilder.toString());
            target.replaceWith(next);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void deleteData(int offset, int count) {
        val stringBuilder = new StringBuilder(target.getData());
        try {
            stringBuilder.delete(offset, offset + count + 1);
            val next = new org.xhtmlrenderer.dom.nodes.Comment(stringBuilder.toString());
            target.replaceWith(next);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void replaceData(int offset, int count, String data) {
        val stringBuilder = new StringBuilder(target.getData());
        try {
            stringBuilder.replace(offset, offset + count + 1, data);
            val next = new org.xhtmlrenderer.dom.nodes.Comment(stringBuilder.toString());
            target.replaceWith(next);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
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

    // region NonDocumentTypeChildNode

    @Override
    public Element previousElementSibling() {
        return nonDocumentTypeChildNodeMixin.previousElementSibling();
    }

    @Override
    public Element nextElementSibling() {
        return nonDocumentTypeChildNodeMixin.nextElementSibling();
    }

    @Override
    public void construct(@DOMString String data) {
        target = new org.xhtmlrenderer.dom.nodes.Comment(data);
    }

    // endregion
}
