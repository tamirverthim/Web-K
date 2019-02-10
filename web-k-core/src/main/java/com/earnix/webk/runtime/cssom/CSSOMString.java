package com.earnix.webk.runtime.cssom;

import com.earnix.webk.runtime.web_idl.Typedef;
import com.earnix.webk.runtime.web_idl.USVString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Typedef(USVString.class)
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface CSSOMString {
}
