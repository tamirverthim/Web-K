package com.earnix.webk.script.web_idl.impl;

import com.earnix.webk.script.web_idl.Typedef;
import com.earnix.webk.util.AssertHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Taras Maslov
 * 11/1/2018
 */
public abstract class MultiTypedef {
    
    @Getter
    @Setter
    private Object value;

    public void set(Object value) {
        val types = getClass().getAnnotation(Typedef.class).value();
        AssertHelper.assertState(ArrayUtils.contains(types, value));
        this.value = value;
    }

    public <T> T get() {
        return (T) value;
    }

    public boolean is(Class<?> clazz) {
        return value.getClass().equals(clazz);
    }
}
