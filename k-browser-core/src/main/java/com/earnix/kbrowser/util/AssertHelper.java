package com.earnix.kbrowser.util;

/**
 * @author Taras Maslov
 * 7/11/2018
 */
public class AssertHelper {

    public static void assertState(boolean condition) {
        if (!condition) {
            throw new IllegalStateException("Must be true");
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
