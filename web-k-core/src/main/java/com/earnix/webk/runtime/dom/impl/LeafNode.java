package com.earnix.webk.runtime.dom.impl;

import com.earnix.webk.runtime.dom.impl.helper.Validate;
import com.earnix.webk.runtime.dom.impl.nodes.AttributesModel;

import java.util.Collections;
import java.util.List;

/**
 * @author Taras Maslov
 * 2/10/2019
 */
public abstract class LeafNode extends NodeImpl {
    
    private static final List<NodeImpl> EmptyNodes = Collections.emptyList();

    protected Object value; // either a string value, or an attribute map (in the rare case multiple attributes are set)

    protected final boolean hasAttributes() {
        return value instanceof AttributesModel;
    }

    @Override
    public final AttributesModel getAttributes() {
        ensureAttributes();
        return (AttributesModel) value;
    }

    private void ensureAttributes() {
        if (!hasAttributes()) {
            Object coreValue = value;
            AttributesModel attributes = new AttributesModel();
            value = attributes;
            if (coreValue != null)
                attributes.put(nodeName(), (String) coreValue);
        }
    }

    public String coreValue() {
        return attr(nodeName());
    }

    protected void coreValue(String value) {
        attr(nodeName(), value);
    }

    @Override
    public String attr(String key) {
        Validate.notNull(key);
        if (!hasAttributes()) {
            return key.equals(nodeName()) ? (String) value : EmptyString;
        }
        return super.attr(key);
    }

    @Override
    public NodeImpl attr(String key, String value) {
        if (!hasAttributes() && key.equals(nodeName())) {
            this.value = value;
        } else {
            ensureAttributes();
            super.attr(key, value);
        }
        return this;
    }

    @Override
    public boolean hasAttr(String key) {
        ensureAttributes();
        return super.hasAttr(key);
    }

    @Override
    public NodeImpl removeAttr(String key) {
        ensureAttributes();
        return super.removeAttr(key);
    }

    @Override
    public String absUrl(String key) {
        ensureAttributes();
        return super.absUrl(key);
    }

    @Override
    public String baseUri() {
        return hasParent() ? parent().baseUri() : "";
    }

    @Override
    protected void doSetBaseUri(String baseUri) {
        // noop
    }

    @Override
    public int childNodeSize() {
        return 0;
    }

    @Override
    public List<NodeImpl> ensureChildNodes() {
        return EmptyNodes;
    }
}
