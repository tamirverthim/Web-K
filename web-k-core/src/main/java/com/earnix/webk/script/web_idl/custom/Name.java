package com.earnix.webk.script.web_idl.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define field (or other code entity) name if it conflicts with Java keyword
 * // todo handle in adapter
 * 
 * @author Taras Maslov
 * 10/31/2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface Name {
    String value();
}
