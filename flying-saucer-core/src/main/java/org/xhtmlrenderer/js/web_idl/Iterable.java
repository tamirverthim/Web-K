package org.xhtmlrenderer.js.web_idl;

/**
 * @author Taras Maslov
 * 7/27/2018
 */
public interface Iterable<T> {
    @Nullable
    T item(@Unsigned int index);

    @ReadonlyAttribute
    @Unsigned
    int length();
}
