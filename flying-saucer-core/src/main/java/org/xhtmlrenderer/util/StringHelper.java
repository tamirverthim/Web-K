package org.xhtmlrenderer.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Taras Maslov
 * 6/18/2018
 */
public class StringHelper {
    public static String nullIfEmpty(String value) {
        return StringUtils.isNotBlank(value) ? value : null;
    }
}
