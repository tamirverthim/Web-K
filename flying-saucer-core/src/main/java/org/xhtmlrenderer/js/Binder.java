package org.xhtmlrenderer.js;

import org.w3c.dom.Element;

import java.util.HashMap;

/**
 * @author Taras Maslov
 * 6/4/2018
 */
public class Binder {
    private static final HashMap<Element, org.xhtmlrenderer.js.dom.Element> registry = new HashMap<>();

    public static org.xhtmlrenderer.js.dom.Element get(Object key) {
        return registry.get(key);
    }

    public static org.xhtmlrenderer.js.dom.Element put(Element key, org.xhtmlrenderer.js.dom.Element value) {
        return registry.put(key, value);
    }

    public static boolean remove(Object key, Object value) {
        return registry.remove(key, value);
    }
}
