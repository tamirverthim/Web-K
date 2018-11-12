package com.earnix.webk.script.web_idl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constructor {
    /**
     * Constructor method name
     */
    String value() default "constructor";

    /**
     * Zero-indexed optional arguments
     */
//    int[] optionalArgs() default {};
}
