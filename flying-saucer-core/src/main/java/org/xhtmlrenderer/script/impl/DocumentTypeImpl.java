package org.xhtmlrenderer.script.impl;

import org.xhtmlrenderer.script.whatwg_dom.DocumentType;
import org.xhtmlrenderer.swing.BasicPanel;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
public class DocumentTypeImpl extends NodeImpl implements DocumentType {
    
    private org.xhtmlrenderer.dom.nodes.DocumentType target;

    public DocumentTypeImpl(org.xhtmlrenderer.dom.nodes.DocumentType target, BasicPanel panel) {
        super(target, panel);
        this.target = target;
    }

    @Override
    public String name() {
        return target.attr("name");
    }

    @Override
    public String publicId() {
        return target.attr("publicId");
    }

    @Override
    public String systemId() {
        return target.attr("systemId");
    }
}
