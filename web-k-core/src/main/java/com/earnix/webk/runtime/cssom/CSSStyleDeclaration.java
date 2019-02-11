package com.earnix.webk.runtime.cssom;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.CEReactions;
import com.earnix.webk.runtime.web_idl.DefaultString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Getter;
import com.earnix.webk.runtime.web_idl.NullTreat;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.TreatNullAs;
import com.earnix.webk.runtime.web_idl.Unsigned;
import com.earnix.webk.runtime.web_idl.impl.PropertyGetter;
import com.earnix.webk.runtime.web_idl.impl.PropertySetter;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Exposed(Window.class)
public interface CSSStyleDeclaration {

    @CEReactions
    @CSSOMString
    Attribute<String> cssText();

    @ReadonlyAttribute
    @Unsigned
    int length();

    @Getter
    @CSSOMString
    String item(@Unsigned int index);

    @PropertyGetter
    @CSSOMString
    String getPropertyValue(@CSSOMString String property);

    @CSSOMString
    String getPropertyPriority(@CSSOMString String property);


    @PropertySetter
    @CEReactions
    void setProperty(
            @CSSOMString String property,
            @TreatNullAs(NullTreat.EmptyString) @CSSOMString String value,
            @TreatNullAs(NullTreat.EmptyString) @Optional @DefaultString("") @CSSOMString String priority
    );

    @CEReactions
    @CSSOMString
    String removeProperty(@CSSOMString String property);

    @ReadonlyAttribute
    @Nullable
    CSSRule parentRule();

    @CEReactions
    @TreatNullAs(NullTreat.EmptyString)
    @CSSOMString
    Attribute<String> cssFloat();
}
