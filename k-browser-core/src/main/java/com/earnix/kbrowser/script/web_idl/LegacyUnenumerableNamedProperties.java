package com.earnix.kbrowser.script.web_idl;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public interface LegacyUnenumerableNamedProperties {
    Object item(int index);

    Object namedItem(@DOMString String name);
}
