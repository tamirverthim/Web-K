package com.earnix.webk.script.web_idl;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks String / Attribute<String> / @OneOf  / com.earnix.webk.script.web_idl.Sequence
 *
 * @author Taras Maslov
 * 5/29/2018
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DOMString {
    int oneOfIndex() default -1;
}
