package com.earnix.webk.dom.nodes;

import com.earnix.webk.script.impl.LeafNode;
import com.earnix.webk.script.html.impl.DocumentImpl;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * A data node, for contents of style, script tags etc, where contents should not show in text().
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
@Slf4j
public class DataImpl extends LeafNode {

    /**
     * Create a new DataNode.
     *
     * @param data data contents
     */
    public DataImpl(String data) {
        value = data;
    }

    /**
     * Create a new DataNode.
     *
     * @param data    data contents
     * @param baseUri Unused, Leaf Nodes do not hold base URis
     * @deprecated use {@link #DataImpl(String)} instead
     */
    public DataImpl(String data, String baseUri) {
        this(data);
    }

    public String nodeName() {
        return "#data";
    }

    /**
     * Get the data contents of this node. Will be unescaped and with original new lines, space etc.
     *
     * @return data
     */
    public String getWholeData() {
        return coreValue();
    }

    /**
     * Set the data contents of this node.
     *
     * @param data unencoded data
     * @return this node, for chaining
     */
    public DataImpl setWholeData(String data) {
        coreValue(data);
        return this;
    }

    protected void outerHtmlHead(Appendable accum, int depth, DocumentImpl.OutputSettings out) throws IOException {
        accum.append(getWholeData()); // data is not escaped in return from data nodes, so " in script, style is plain
    }

    protected void outerHtmlTail(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
    }

    @Override
    public String toString() {
        return outerHtml();
    }

    /**
     * Create a new DataNode from HTML encoded data.
     *
     * @param encodedData encoded data
     * @param baseUri     bass URI
     * @return new DataNode
     */
    public static DataImpl createFromEncoded(String encodedData, String baseUri) {
        String data = Entities.unescape(encodedData);
        return new DataImpl(data);
    }

    @Override
    public @DOMString Attribute<String> textContent() {
        return null;
    }

    @Override
    public Node appendChild(Node node) {
        return null;
    }
}
