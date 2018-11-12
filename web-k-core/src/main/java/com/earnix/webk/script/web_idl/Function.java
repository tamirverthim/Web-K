package com.earnix.webk.script.web_idl;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@FunctionalInterface
public interface Function<T> {
    T call(Object ctx, Object[] args);
}
