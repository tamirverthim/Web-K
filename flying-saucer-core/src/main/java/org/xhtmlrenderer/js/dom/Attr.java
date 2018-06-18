package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface Attr {
    @Readonly
    Attribute<DOMString> name();

    @Readonly
    Attribute<Boolean> specified();

    Attribute<DOMString> value();
    // raises(DOMException) on setting

    // Introduced in DOM Level 2:
    @Readonly
    Attribute<Element> ownerElement();

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<TypeInfo> schemaTypeInfo();

    // Introduced in DOM Level 3:
    @Readonly
    Attribute<Boolean> isId();
}
