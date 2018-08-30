package org.xhtmlrenderer.script.impl;

import org.xhtmlrenderer.dom.nodes.Node;
import org.xhtmlrenderer.script.whatwg_dom.ChildNode;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
public class ChildNodeImpl implements ChildNode {
    
    private Node target;

    public ChildNodeImpl(Node target) {
        this.target = target;
    }

    @Override
    public void before(Object... nodes) {
   
    }

    @Override
    public void after(Object... nodes) {

    }

    @Override
    public void replaceWith(Object... nodes) {

    }

    @Override
    public void remove() {
        target.remove();
    }
}
