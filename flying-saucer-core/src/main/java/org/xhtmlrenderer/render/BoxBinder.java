package org.xhtmlrenderer.render;

import org.jsoup.nodes.Element;

import java.util.WeakHashMap;

/**
 * @author Taras Maslov
 * 8/13/2018
 */
public class BoxBinder {
    public static final WeakHashMap<Element, Box> BINDINGS = new WeakHashMap<>();
}
