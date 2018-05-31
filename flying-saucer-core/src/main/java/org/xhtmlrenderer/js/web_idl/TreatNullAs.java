package org.xhtmlrenderer.js.web_idl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface TreatNullAs {
    NullTreat value() default NullTreat.EmptyString;
}
