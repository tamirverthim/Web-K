package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.js.Binder;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMException;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.whatwg_dom.Comment;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.NonDocumentTypeChildNode;

/**
 * @author Taras Maslov
 * 7/13/2018
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentImpl extends NodeImpl implements Comment {
    org.jsoup.nodes.Comment target;

    ChildNodeImpl childNodeMixin;
    NonDocumentTypeChildNode nonDocumentTypeChildNodeMixin;

    Attribute<DOMString> dataAttribute = Attribute.<DOMString>receive(val -> {
        val next = new org.jsoup.nodes.Comment(val.toString());
        target.replaceWith(next);
        Binder.remove(target);
    }).give(() -> DOMStringImpl.of(target.getData()));

    public CommentImpl(org.jsoup.nodes.Comment target) {
        this.target = target;
        childNodeMixin = new ChildNodeImpl(target);
        nonDocumentTypeChildNodeMixin = new NonDocumentTypeChildNodeImpl(target);
    }


    @Override
    public Attribute<DOMString> data() {
        return dataAttribute;
    }

    @Override
    public long length() {
        return target.getData().length();
    }

    @Override
    public DOMString substringData(int offset, int count) {
        val length = target.getData().length();
        try {
            val string = target.getData().substring(offset, offset + count + 1);
            return DOMStringImpl.of(string);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void appendData(DOMString data) {
        val string = data.toString();
        val next = new org.jsoup.nodes.Comment(target.getData() + string);
        target.replaceWith(next);
        Binder.remove(target);
        target = next;
    }

    @Override
    public void insertData(int offset, DOMString data) {
        StringBuilder stringBuilder = new StringBuilder(target.getData());
        try {
            stringBuilder.insert(offset, data.toString());
            val next = new org.jsoup.nodes.Comment(stringBuilder.toString());
            target.replaceWith(next);
            Binder.remove(target);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void deleteData(int offset, int count) {
        val stringBuilder = new StringBuilder(target.getData());
        try {
            stringBuilder.delete(offset, offset + count + 1);
            val next = new org.jsoup.nodes.Comment(stringBuilder.toString());
            target.replaceWith(next);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void replaceData(int offset, int count, DOMString data) {
        val stringBuilder = new StringBuilder(target.getData());
        try {
            stringBuilder.replace(offset, offset + count + 1, data.toString());
            val next = new org.jsoup.nodes.Comment(stringBuilder.toString());
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
    public void construct(DOMString data) {
        target = new org.jsoup.nodes.Comment(data.toString());
    }

    // endregion
}
