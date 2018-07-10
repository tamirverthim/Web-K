package org.xhtmlrenderer.js.impl;

import org.xhtmlrenderer.js.web_idl.USVString;

import java.util.Objects;
import java.util.WeakHashMap;

/**
 * @author Taras Maslov
 * 7/3/2018
 */
public class USVStringImpl implements USVString {
    static WeakHashMap<String, USVStringImpl> cache = new WeakHashMap<>();
    
    private String string;

    private USVStringImpl(String string) {
        this.string = string;
    }

    public static USVStringImpl of(String string){
        USVStringImpl result = cache.get(string);
        if(result == null){
            result = new USVStringImpl(string);
            cache.put(string, result);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        USVStringImpl usvString = (USVStringImpl) o;
        return Objects.equals(string, usvString.string);
    }

    @Override
    public int hashCode() {

        return Objects.hash(string);
    }
}
