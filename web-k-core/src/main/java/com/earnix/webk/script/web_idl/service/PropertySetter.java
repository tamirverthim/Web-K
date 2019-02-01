package com.earnix.webk.script.web_idl.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker for WebIDL interface method to make it default handler of JavaScript object property setting operation.
 * Target method will be called with property name and passed argument when JS object's property is set to that argument.
 *
 * @author Taras Maslov
 * 12/17/2018
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySetter {
}
