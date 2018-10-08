package com.earnix.kbrowser.script.impl;

import com.earnix.kbrowser.dom.nodes.Node;
import com.earnix.kbrowser.script.whatwg_dom.ChildNode;

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
