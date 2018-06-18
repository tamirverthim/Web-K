package org.xhtmlrenderer.js.canvas.impl;

import org.xhtmlrenderer.js.canvas.HitRegionOptions;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.dom.Element;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/5/2018
 */
public class HitRegionOptionsImpl implements HitRegionOptions {
    
    String id;
    
    
    @Override
    public Attribute<DOMString> id() {
        return null;
    }

    @Override
    public Attribute<Element> control() {
        return null;
    }
}
