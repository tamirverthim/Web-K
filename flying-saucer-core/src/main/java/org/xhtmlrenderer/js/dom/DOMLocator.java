package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface DOMLocator {
    @Readonly
    Attribute<Long> lineNumber();

    @Readonly
    Attribute<Long> columnNumber();

    @Readonly
    Attribute<Long> byteOffset();

    @Readonly
    Attribute<Long> utf16Offset();

    @Readonly
    Attribute<Node> relatedNode();

    @Readonly
    Attribute<DOMString> uri();
}
