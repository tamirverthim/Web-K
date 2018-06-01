package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface DOMError {
    // ErrorSeverity
    final @Unsigned short      SEVERITY_WARNING               = 1;
    final @Unsigned short      SEVERITY_ERROR                 = 2;
    final @Unsigned short      SEVERITY_FATAL_ERROR           = 3;

    @Unsigned @Readonly
    Attribute<Short> severity();
    @Readonly
    Attribute< DOMString> message();
    @Readonly
    Attribute< DOMString> type();
    @Readonly
    Attribute< DOMObject> relatedException();
    @Readonly
    Attribute< DOMObject> relatedData();
    @Readonly
    Attribute< DOMLocator> location();
}
