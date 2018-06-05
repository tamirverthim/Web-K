package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface TextMetrics {
    @Readonly Attribute<Double> width();
}
