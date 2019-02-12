package com.earnix.webk.runtime.dom.impl.nodes;

/**
 * A boolean attribute that is written out without any value.
 *
 * @deprecated just use null values (vs empty string) for booleans.
 */
public class BooleanAttributeModel extends AttributeModel {
    /**
     * Create a new boolean attribute from unencoded (raw) key.
     *
     * @param key attribute key
     */
    public BooleanAttributeModel(String key) {
        super(key, null);
    }

    @Override
    protected boolean isBooleanAttribute() {
        return true;
    }
}
