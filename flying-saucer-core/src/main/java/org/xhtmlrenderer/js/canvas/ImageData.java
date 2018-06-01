package org.xhtmlrenderer.js.canvas;

import lombok.Getter;
import org.xhtmlrenderer.js.Uint8ClampedArray;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface ImageData {

    @Readonly
    @Unsigned
    Attribute<Long> width();

    @Readonly
    @Unsigned
    Attribute<Long> height();

    @Readonly
    Attribute<Uint8ClampedArray> data();
}
