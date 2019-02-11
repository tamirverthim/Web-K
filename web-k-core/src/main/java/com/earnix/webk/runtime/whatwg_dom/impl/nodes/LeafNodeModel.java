//package com.earnix.webk.dom.nodes;
//
//import com.earnix.webk.dom.helper.Validate;
//
//import java.util.Collections;
//import java.util.List;
//
//abstract class LeafNodeModel extends NodeModel {
//    private static final List<NodeModel> EmptyNodes = Collections.emptyList();
//
//    Object value; // either a string value, or an attribute map (in the rare case multiple attributes are set)
//
//    protected final boolean hasAttributes() {
//        return value instanceof AttributesModel;
//    }
//
//    @Override
//    public final AttributesModel attributes() {
//        ensureAttributes();
//        return (AttributesModel) value;
//    }
//
//    private void ensureAttributes() {
//        if (!hasAttributes()) {
//            Object coreValue = value;
//            AttributesModel attributes = new AttributesModel();
//            value = attributes;
//            if (coreValue != null)
//                attributes.put(nodeName(), (String) coreValue);
//        }
//    }
//
//    String coreValue() {
//        return attr(nodeName());
//    }
//
//    void coreValue(String value) {
//        attr(nodeName(), value);
//    }
//
//    @Override
//    public String attr(String key) {
//        Validate.notNull(key);
//        if (!hasAttributes()) {
//            return key.equals(nodeName()) ? (String) value : EmptyString;
//        }
//        return super.attr(key);
//    }
//
//    @Override
//    public NodeModel attr(String key, String value) {
//        if (!hasAttributes() && key.equals(nodeName())) {
//            this.value = value;
//        } else {
//            ensureAttributes();
//            super.attr(key, value);
//        }
//        return this;
//    }
//
//    @Override
//    public boolean hasAttr(String key) {
//        ensureAttributes();
//        return super.hasAttr(key);
//    }
//
//    @Override
//    public NodeModel removeAttr(String key) {
//        ensureAttributes();
//        return super.removeAttr(key);
//    }
//
//    @Override
//    public String absUrl(String key) {
//        ensureAttributes();
//        return super.absUrl(key);
//    }
//
//    @Override
//    public String baseUri() {
//        return hasParent() ? parent().baseUri() : "";
//    }
//
//    @Override
//    protected void doSetBaseUri(String baseUri) {
//        // noop
//    }
//
//    @Override
//    public int childNodeSize() {
//        return 0;
//    }
//
//    @Override
//    protected List<NodeModel> ensureChildNodes() {
//        return EmptyNodes;
//    }
//}
