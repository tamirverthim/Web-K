package com.earnix.webk.script.web_idl;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Taras Maslov
 * 7/27/2018
 */
public interface Iterable<T> extends java.lang.Iterable {
    @Nullable
    T item(@Unsigned int index);

    @ReadonlyAttribute
    @Unsigned
    int length();

    @Override
    default Iterator<T> iterator() {
        return new Iterator<T>() {
            int index;

            @Override
            public boolean hasNext() {
                return index != length() - 1;
            }

            @Override
            public T next() {
                index++;
                if (length() < index) {
                    throw new NoSuchElementException();
                }
                return item(index - 1);
            }
        };
    }
}
