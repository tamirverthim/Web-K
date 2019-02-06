package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.SerializationException;
import com.earnix.webk.dom.helper.Validate;
import com.earnix.webk.dom.internal.StringUtil;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.script.html.impl.DocumentImpl;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Node;

import java.io.IOException;

/**
 * An XML Declaration.
 */
public class XmlDeclarationModel extends NodeImpl {
    // todo this impl isn't really right, the data shouldn't be attributes, just a run of text after the name
    private final boolean isProcessingInstruction; // <! if true, <? if false, declaration (and last data char should be ?)

    /**
     * Create a new XML declaration
     *
     * @param name                    of declaration
     * @param isProcessingInstruction is processing instruction
     */
    public XmlDeclarationModel(String name, boolean isProcessingInstruction) {
        Validate.notNull(name);
        value = name;
        this.isProcessingInstruction = isProcessingInstruction;
    }

    /**
     * Create a new XML declaration
     *
     * @param name                    of declaration
     * @param baseUri                 Leaf Nodes don't have base URIs; they inherit from their Element
     * @param isProcessingInstruction is processing instruction
     * @see XmlDeclarationModel#XmlDeclarationModel(String, boolean)
     * @deprecated
     */
    public XmlDeclarationModel(String name, String baseUri, boolean isProcessingInstruction) {
        this(name, isProcessingInstruction);
    }

    public String nodeName() {
        return "#declaration";
    }

    @Override
    public @DOMString Attribute<String> textContent() {
        return null;
    }

    @Override
    public Node appendChild(Node node) {
        throw new DOMException("Unsupported");
    }

    /**
     * Get the name of this declaration.
     *
     * @return name of this declaration.
     */
    public String name() {
        return coreValue();
    }

    /**
     * Get the unencoded XML declaration.
     *
     * @return XML declaration
     */
    public String getWholeDeclaration() {
        StringBuilder sb = StringUtil.borrowBuilder();
        try {
            getWholeDeclaration(sb, new DocumentImpl.OutputSettings());
        } catch (IOException e) {
            throw new SerializationException(e);
        }
        return StringUtil.releaseBuilder(sb).trim();
    }

    private void getWholeDeclaration(Appendable accum, DocumentImpl.OutputSettings out) throws IOException {
        for (AttributeModel attribute : getAttributes()) {
            if (!attribute.getKey().equals(nodeName())) { // skips coreValue (name)
                accum.append(' ');
                attribute.html(accum, out);
            }
        }
    }

    protected void outerHtmlHead(Appendable accum, int depth, DocumentImpl.OutputSettings out) throws IOException {
        accum
                .append("<")
                .append(isProcessingInstruction ? "!" : "?")
                .append(coreValue());
        getWholeDeclaration(accum, out);
        accum
                .append(isProcessingInstruction ? "!" : "?")
                .append(">");
    }

    protected void outerHtmlTail(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
    }

    @Override
    public String toString() {
        return outerHtml();
    }
}
