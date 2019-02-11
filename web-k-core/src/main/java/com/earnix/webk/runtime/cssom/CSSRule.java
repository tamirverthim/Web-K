package com.earnix.webk.runtime.cssom;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Exposed(Window.class)
public interface CSSRule {
    @Unsigned final short STYLE_RULE = 1;
    @Unsigned final short CHARSET_RULE = 2; // historical
    @Unsigned final short IMPORT_RULE = 3;
    @Unsigned final short MEDIA_RULE = 4;
    @Unsigned final short FONT_FACE_RULE = 5;
    @Unsigned final short PAGE_RULE = 6;
    @Unsigned final short MARGIN_RULE = 9;
    @Unsigned final short NAMESPACE_RULE = 10;

    @Unsigned
    @ReadonlyAttribute
    Short type();

    @CSSOMString
    Attribute<String> cssText();

    @ReadonlyAttribute
    @Nullable
    CSSRule parentRule();

    @Nullable
    @ReadonlyAttribute
    CSSStyleSheet parentStyleSheet();
}
