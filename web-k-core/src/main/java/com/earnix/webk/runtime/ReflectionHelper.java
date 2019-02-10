package com.earnix.webk.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
public class ReflectionHelper {

    public static <T> Object create(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object[] addNullsToMatchLength(int length, Object... source) {
        if (source.length == length) {
            return source;
        }

        if (source.length > length) {
            throw new IllegalArgumentException();
        }

        Object[] result = new Object[length];
        System.arraycopy(source, 0, result, 0, source.length);
        return result;
    }

    /**
     * Invokes given method replacing its last missing arguments with null's.
     *
     * @see Method#invoke(Object, Object...)
     */
    public static Object relaxedInvoke(Object target, Method method, Object... args) {
        args = addNullsToMatchLength(method.getParameterCount(), args);
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
