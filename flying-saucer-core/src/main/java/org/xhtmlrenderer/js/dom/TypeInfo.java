package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.web_idl.Readonly;
import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface TypeInfo {
    @Readonly
    Attribute<DOMString> typeName();

    @Readonly
    Attribute<DOMString> typeNamespace();

    // DerivationMethods
    final @Unsigned long DERIVATION_RESTRICTION = 0x00000001;
    final @Unsigned long DERIVATION_EXTENSION = 0x00000002;
    final @Unsigned long DERIVATION_UNION = 0x00000004;
    final @Unsigned long DERIVATION_LIST = 0x00000008;

    boolean isDerivedFrom(DOMString typeNamespaceArg,
                          DOMString typeNameArg,
                          @Unsigned long derivationMethod);
}
