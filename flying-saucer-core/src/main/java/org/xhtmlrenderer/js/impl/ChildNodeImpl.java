package org.xhtmlrenderer.js.impl;

import org.jsoup.nodes.Node;
import org.xhtmlrenderer.js.whatwg_dom.ChildNode;

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
