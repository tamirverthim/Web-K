package org.xhtmlrenderer.js.dom.impl;

import org.xhtmlrenderer.js.dom.DocumentFragment;

/**
 * @author Taras Maslov
 * 6/4/2018
 */
public class DocumentFragmentImpl extends NodeImpl implements DocumentFragment {
    public DocumentFragmentImpl(org.w3c.dom.DocumentFragment target) {
        super(target);
    }
}
