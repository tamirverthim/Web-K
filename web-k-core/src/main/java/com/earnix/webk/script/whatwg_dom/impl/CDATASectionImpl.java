package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.TextNodeModel;
import com.earnix.webk.script.whatwg_dom.CDATASection;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
public class CDATASectionImpl extends TextImpl implements CDATASection {
    
    public CDATASectionImpl(TextNodeModel target) {
        super(target);
    }
    
}