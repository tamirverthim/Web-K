package com.earnix.webk.script.cssom;

import com.earnix.webk.script.web_idl.DefaultLong;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.SameObject;
import com.earnix.webk.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Exposed(Window.class)
public interface CSSStyleSheet {
    @ReadonlyAttribute
    @Nullable
    CSSRule ownerRule();

    @SameObject
    @ReadonlyAttribute
    CSSRuleList cssRules();

    @Unsigned
    int insertRule(@CSSOMString String rule, @Optional @Unsigned @DefaultLong(0) int index);

    void deleteRule(@Unsigned int index);
}
