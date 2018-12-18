package com.earnix.webk.util;

/**
 * @author Taras Maslov
 * 7/11/2018
 */
public class AssertHelper {

    public static void assertState(boolean condition) {
        assertState("Must be true");
    }

    public static void assertState(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    public static void assertState(Object object) {
        assertState(object, "Must be not null");
    }

    public static void assertState(Object object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
    }
    
    public static void assertArgument(boolean condition) {
        if (!condition) {
            throw new IllegalStateException("Must be true");
        }
    }

    public static void assertNotNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new NullPointerException("Must be not null");
            }
        }
    }
}
