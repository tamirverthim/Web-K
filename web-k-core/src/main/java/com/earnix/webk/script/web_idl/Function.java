package com.earnix.webk.script.web_idl;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
public interface Function<T, K> {
    T call(K[] args);
}
