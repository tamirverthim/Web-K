package org.xhtmlrenderer.js.web_idl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
public interface LegacyUnenumerableNamedProperties {
    Object item(int index);
    Object namedItem(DOMString name);
}
