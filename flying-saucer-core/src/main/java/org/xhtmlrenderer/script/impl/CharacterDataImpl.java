package org.xhtmlrenderer.script.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.xhtmlrenderer.dom.nodes.CDataNode;
import org.xhtmlrenderer.script.web_idl.Attribute;
import org.xhtmlrenderer.script.web_idl.DOMException;
import org.xhtmlrenderer.script.whatwg_dom.*;
import org.xhtmlrenderer.swing.BasicPanel;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterDataImpl extends NodeImpl implements CharacterData {
    
    CDataNode target;
    ChildNodeImpl childNodeMixin;
    NonDocumentTypeChildNode nonDocumentTypeChildNodeMixin;
    
    Attribute<String> dataAttribute = Attribute.<String>receive(val -> target.text(val)).give(() -> target.getWholeText());
    
    public CharacterDataImpl(CDataNode target, BasicPanel panel) {
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
        return target.getWholeText().length();
    }

    @Override
    public String substringData(int offset, int count) {
        try {
            return target.getWholeText().substring(offset, offset + count + 1);
        } catch (StringIndexOutOfBoundsException e){
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void appendData(String data) {
        target.text(target.getWholeText() + data);
    }

    @Override
    public void insertData(int offset, String data) {
        StringBuilder stringBuilder = new StringBuilder(target.getWholeText());
        try {
            stringBuilder.insert(offset, data);
            target.text(stringBuilder.toString());
        } catch (StringIndexOutOfBoundsException e){
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void deleteData(int offset, int count) {
        val stringBuilder = new StringBuilder(target.getWholeText());
        try {
            stringBuilder.delete(offset, offset + count + 1);
            target.text(stringBuilder.toString());
        } catch (StringIndexOutOfBoundsException e){
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void replaceData(int offset, int count, String data) {
        val stringBuilder = new StringBuilder(target.getWholeText());
        try {
            stringBuilder.replace(offset, offset + count + 1, data);
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
