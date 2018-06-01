package org.xhtmlrenderer.js.dom.impl;

import lombok.AllArgsConstructor;
import org.xhtmlrenderer.js.dom.Node;
import org.xhtmlrenderer.js.dom.NodeList;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
@AllArgsConstructor
public class NodeListImpl implements NodeList {
    
    private org.w3c.dom.NodeList impl;
    
    @Override
    public Node item(long index) {
        return new NodeImpl(impl.item((int) index));
    }

    @Override
    public Attribute<Long> length() {
        return Attribute.readOnly().give(() -> impl.getLength());
    }
}
