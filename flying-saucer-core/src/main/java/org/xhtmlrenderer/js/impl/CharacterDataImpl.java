package org.xhtmlrenderer.js.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.jsoup.nodes.CDataNode;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMException;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.whatwg_dom.*;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterDataImpl extends NodeImpl implements CharacterData {
    
    CDataNode target;
    ChildNodeImpl childNodeMixin;
    NonDocumentTypeChildNode nonDocumentTypeChildNodeMixin;
    
    Attribute<DOMString> dataAttribute = Attribute.<DOMString>receive(val -> target.text(val.toString())).give(() -> DOMStringImpl.of(target.getWholeText()));
    
    public CharacterDataImpl(CDataNode target, XHTMLPanel panel) {
        super(target, panel);
        this.target = target;
        childNodeMixin = new ChildNodeImpl(target);
        nonDocumentTypeChildNodeMixin = new NonDocumentTypeChildNodeImpl(target, panel);
    }


    @Override
    public Attribute<DOMString> data() {
        return dataAttribute;
    }

    @Override
    public long length() {
        return target.getWholeText().length();
    }

    @Override
    public DOMString substringData(int offset, int count) {
        val length = target.getWholeText().length();
        try {
            val string = target.getWholeText().substring(offset, offset + count + 1);
            return DOMStringImpl.of(string);
        } catch (StringIndexOutOfBoundsException e){
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void appendData(DOMString data) {
        val string = data.toString();
        target.text(target.text() + string);
    }

    @Override
    public void insertData(int offset, DOMString data) {
        StringBuilder stringBuilder = new StringBuilder(target.text());
        try {
            stringBuilder.insert(offset, data.toString());
            target.text(stringBuilder.toString());
        } catch (StringIndexOutOfBoundsException e){
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void deleteData(int offset, int count) {
        val stringBuilder = new StringBuilder(target.text());
        try {
            stringBuilder.delete(offset, offset + count + 1);
            target.text(stringBuilder.toString());
        } catch (StringIndexOutOfBoundsException e){
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void replaceData(int offset, int count, DOMString data) {
        val stringBuilder = new StringBuilder(target.text());
        try {
            stringBuilder.replace(offset, offset + count + 1, data.toString());
            target.text(stringBuilder.toString());
        } catch (StringIndexOutOfBoundsException e){
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

    // endregion
}
