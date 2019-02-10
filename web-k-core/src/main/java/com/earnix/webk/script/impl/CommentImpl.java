package com.earnix.webk.script.impl;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.helper.Validate;
import com.earnix.webk.dom.nodes.NodeModelUtils;
import com.earnix.webk.dom.nodes.XmlDeclarationModel;
import com.earnix.webk.dom.parser.Parser;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.Node;
import com.earnix.webk.script.whatwg_dom.NonDocumentTypeChildNode;
import com.earnix.webk.script.html.impl.DocumentImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.io.IOException;

/**
 * @author Taras Maslov
 * 7/13/2018
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentImpl extends LeafNode implements com.earnix.webk.script.whatwg_dom.Comment {

    NonDocumentTypeChildNode nonDocumentTypeChildNodeMixin;

    Attribute<String> dataAttribute = Attribute.<String>receive(val -> {
        val next = new CommentImpl(val);
        replaceWith(next);
    }).give(this::getData);

    private static final String COMMENT_KEY = "comment";
    
    public CommentImpl() {
        super();
        nonDocumentTypeChildNodeMixin = new NonDocumentTypeChildNodeImpl(this);
    }


    @Override
    public Attribute<String> data() {
        return dataAttribute;
    }

    @Override
    public long length() {
        return getData().length();
    }

    @Override
    public String substringData(int offset, int count) {
        try {
            return getData().substring(offset, offset + count + 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void appendData(String data) {
        val next = new CommentImpl(getData() + data);
        replaceWith(next);
    }

    @Override
    public void insertData(int offset, String data) {
        StringBuilder stringBuilder = new StringBuilder(getData());
        try {
            stringBuilder.insert(offset, data);
            val next = new CommentImpl(stringBuilder.toString());
            replaceWith(next);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void deleteData(int offset, int count) {
        val stringBuilder = new StringBuilder(getData());
        try {
            stringBuilder.delete(offset, offset + count + 1);
            val next = new CommentImpl(stringBuilder.toString());
            replaceWith(next);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    @Override
    public void replaceData(int offset, int count, String data) {
        val stringBuilder = new StringBuilder(getData());
        try {
            stringBuilder.replace(offset, offset + count + 1, data);
            val next = new CommentImpl(stringBuilder.toString());
            replaceWith(next);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("RangeError");
        }
    }

    // region ChildNode

    @Override
    public void before(Object... nodes) {
    }

    @Override
    public void after(Object... nodes) {
    }

    @Override
    public void replaceWith(Object... nodes) {
    }

    // endregion

    // region NonDocumentTypeChildNode

    @Override
    public Element previousElementSibling() {
        return nonDocumentTypeChildNodeMixin.previousElementSibling();
    }

    @Override
    public Element nextElementSibling() {
        return nonDocumentTypeChildNodeMixin.nextElementSibling();
    }

    @Override
    public void constructor(@DOMString String data) {
    }

    // endregion


    @Override
    public @DOMString Attribute<String> textContent() {
        return new Attribute<String>() {

            @Override
            public String get() {
                return getData();
            }

            @Override
            public void set(String s) {
                if (s != null) {
                    setData(s);
                } else {
                    getData();
                }
            }

        };
    }

    @Override
    public Node appendChild(Node node) {
        throw new DOMException("HierarchyRequestError");
    }

    // region model

    /**
     * Create a new comment node.
     *
     * @param data The contents of the comment
     */
    public CommentImpl(String data) {
        value = data;
    }

    /**
     * Create a new comment node.
     *
     * @param data    The contents of the comment
     * @param baseUri base URI not used. This is a leaf node.
     * @deprecated
     */
    public CommentImpl(String data, String baseUri) {
        this(data);
    }

    public String nodeName() {
        return "#comment";
    }

    /**
     * Get the contents of the comment.
     *
     * @return comment content
     */
    public String getData() {
        return coreValue();
    }

    public void setData(String data) {
        Validate.notNull(data);
        coreValue(data);
    }

    protected void outerHtmlHead(Appendable accum, int depth, DocumentImpl.OutputSettings out) throws IOException {
        if (out.prettyPrint())
            indent(accum, depth, out);
        accum
                .append("<!--")
                .append(getData())
                .append("-->");
    }

    protected void outerHtmlTail(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
    }

    @Override
    public String toString() {
        return outerHtml();
    }

    /**
     * Check if this comment looks like an XML Declaration.
     *
     * @return true if it looks like, maybe, it's an XML Declaration.
     */
    public boolean isXmlDeclaration() {
        String data = getData();
        return (data.length() > 1 && (data.startsWith("!") || data.startsWith("?")));
    }

    /**
     * Attempt to cast this comment to an XML Declaration note.
     *
     * @return an XML declaration if it could be parsed as one, null otherwise.
     */
    public XmlDeclarationModel asXmlDeclaration() {
        String data = getData();
        DocumentImpl doc = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", baseUri(), Parser.xmlParser());
        XmlDeclarationModel decl = null;
        if (doc.childNodeSize() > 0) {
            ElementImpl el = doc.child(0);
            decl = new XmlDeclarationModel(NodeModelUtils.parser(doc).settings().normalizeTag(el.tagName()), data.startsWith("!"));
            decl.getAttributes().addAll(el.getAttributes());
        }
        return decl;
    }
    
    // endregion
}
