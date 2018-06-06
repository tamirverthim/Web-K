package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.Element;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DefaultString;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface HitRegionOptions {
    // dictionary to allow expansion on Hit Regions in Canvas Context 2D Level 2
    @DefaultString("") Attribute<DOMString> id();
    // for control-backed regions:
    @Optional Attribute<Element> control();
}
