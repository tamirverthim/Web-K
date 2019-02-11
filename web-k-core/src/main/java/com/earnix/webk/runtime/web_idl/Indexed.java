package com.earnix.webk.runtime.web_idl;

/**
 * @author Taras Maslov
 * 6/4/2018
 */
public interface Indexed<T> {
    T elementAtIndex(long idx);
}
