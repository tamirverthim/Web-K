package com.earnix.webk.script.web_idl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Informative: target Object type may be null or undefined or String or Symbol or boolean. As well as Object.
 * 
 * @author Taras Maslov
 * 11/11/2018
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Any {
}
