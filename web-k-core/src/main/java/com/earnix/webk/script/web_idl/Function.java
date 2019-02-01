package com.earnix.webk.script.web_idl;

/**
 * Function member of {@link com.earnix.webk.script.web_idl.impl.WebIDLAdapter}
 * 
 * @author Taras Maslov
 * 10/31/2018
 */
@FunctionalInterface
public interface Function<T> {

    /**
     * @param ctx JS context object
     * @param args JS objects
     */
    T call(Object ctx, Object[] args);
}
