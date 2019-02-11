package com.earnix.webk.runtime.web_idl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark read-only properties in WebIDL interfaces.
 * 
 * @author Taras Maslov
 * 6/21/2018
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadonlyAttribute {
}
