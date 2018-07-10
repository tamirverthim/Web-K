package org.xhtmlrenderer.js;


import org.xhtmlrenderer.js.whatwg_dom.Element;

import java.util.HashMap;

/**
 * Binds parser classes and JS DOM implementations
 * 
 * @author Taras Maslov
 * 6/4/2018
 */
public class Binder {
    private static final HashMap<org.jsoup.nodes.Element, Element> registry = new HashMap<>();

    public static Element get(org.jsoup.nodes.Element key) {
        return registry.get(key);
    }

    public static Element put(org.jsoup.nodes.Element key, Element value) {
        return registry.put(key, value);
    }

    public static void remove(org.jsoup.nodes.Element key) {
        registry.remove(key);
    }
}
