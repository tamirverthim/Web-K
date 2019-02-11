package com.earnix.webk.runtime.web_idl.impl;

import com.earnix.webk.runtime.web_idl.Typedef;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.stream.Stream;

/**
 * @author Taras Maslov
 * 11/1/2018
 */
public abstract class MultiTypedef {
    
    @Getter
    @Setter
    private Object value;

    public void set(Object value) {
        val correct = Stream.of(getClass().getAnnotation(Typedef.class).value()).anyMatch(clz -> clz.isAssignableFrom(value.getClass()));
        if(!correct){
            throw new RuntimeException();
        } 
        this.value = value;
    }

    public <T> T get() {
        return (T) value;
    }

    public boolean is(Class<?> clazz) {
        return clazz.isAssignableFrom(value.getClass());
    }
}
