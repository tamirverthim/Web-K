package org.xhtmlrenderer.js.web_idl;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
public @interface OneOf {
    Class[] value() default Object.class;
}
