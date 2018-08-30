package org.xhtmlrenderer.script;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
public class ReflectionHelper {
    public static <T> Object create(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
