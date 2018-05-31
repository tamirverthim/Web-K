package org.xhtmlrenderer.js.web_idl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    boolean readonly() default false;
    String defaultString() default "";
    int defaultInt() default 0;

    boolean unsigned() default false;
}
