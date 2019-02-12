package com.earnix.webk.runtime.dom.impl.parser;

import com.earnix.webk.runtime.dom.impl.nodes.AttributesModel;

import static com.earnix.webk.runtime.dom.impl.internal.Normalizer.lowerCase;

/**
 * Controls parser settings, to optionally preserve tag and/or attribute name case.
 */
public class ParseSettings {
    /**
     * HTML default settings: both tag and attribute names are lower-cased during parsing.
     */
    public static final ParseSettings htmlDefault;
    /**
     * Preserve both tag and attribute case.
     */
    public static final ParseSettings preserveCase;

    static {
        htmlDefault = new ParseSettings(false, false);
        preserveCase = new ParseSettings(true, true);
    }

    private final boolean preserveTagCase;
    private final boolean preserveAttributeCase;

    /**
     * Define parse settings.
     *
     * @param tag       preserve tag case?
     * @param attribute preserve attribute name case?
     */
    public ParseSettings(boolean tag, boolean attribute) {
        preserveTagCase = tag;
        preserveAttributeCase = attribute;
    }

    public String normalizeTag(String name) {
        name = name.trim();
        if (!preserveTagCase)
            name = lowerCase(name);
        return name;
    }

    public String normalizeAttribute(String name) {
        name = name.trim();
        if (!preserveAttributeCase)
            name = lowerCase(name);
        return name;
    }

    AttributesModel normalizeAttributes(AttributesModel attributes) {
        if (!preserveAttributeCase) {
            attributes.normalize();
        }
        return attributes;
    }
}
