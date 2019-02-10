package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.helper.Validate;
import com.earnix.webk.dom.internal.StringUtil;
import com.earnix.webk.dom.nodes.Entities;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.html.canvas.HTMLSlotElement;
//import com.earnix.webk.script.impl.ChildNodeImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.LeafNode;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.script.impl.NonDocumentTypeChildNodeImpl;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMException;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.Node;
import com.earnix.webk.script.whatwg_dom.Text;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.io.IOException;

/**
 * @author Taras Maslov
 * 7/24/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextImpl extends LeafNode implements Text {

//    ChildNodeImpl childNodeMixin;
    NonDocumentTypeChildNodeImpl nonDocumentTypeChildNodeMixin;

    // text

    public TextImpl() {
//        this.childNodeMixin = new ChildNodeImpl(this);
        this.nonDocumentTypeChildNodeMixin = new NonDocumentTypeChildNodeImpl(this);
    }

    /**
     * Create a new TextNode representing the supplied (unencoded) text).
     *
     * @param text raw text
     * @see #createFromEncoded(String)
     */
    public TextImpl(String text) {
        value = text;
    }

    /**
     * Create a new TextNode representing the supplied (unencoded) text).
     *
     * @param text    raw text
     * @param baseUri base uri - ignored for this node type
     * @see #createFromEncoded(String, String)
     * @deprecated use {@link TextImpl#TextImpl(String, String)}
     */
    public TextImpl(String text, String baseUri) {
        this(text);
    }
    
    @Override
    public void constructor(@DOMString String data) {

    }

    @Override
    public Text splitText(long offset) {
        return null;
    }

    @Override
    public String wholeText() {
        return text();
    }

    // cdata

    @Override
    public Attribute<String> data() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return getWholeText();
            }

            @Override
            public void set(String s) {
                text(s);
            }
        };
    }

    @Override
    public long length() {
        return getWholeText().length();
    }

    @Override
    public String substringData(int offset, int count) {
        try {
            return getWholeText().substring(offset, offset + count + 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DOMException("Range error");
        }
    }

    @Override
    public void appendData(String data) {
        text(getWholeText() + data);
    }

    @Override
    public void insertData(int offset, String data) {
        val sb = new StringBuilder(getWholeText());
        sb.insert(offset, data);
        text(sb.toString());
    }

    @Override
    public void deleteData(int offset, int count) {
        val sb = new StringBuilder(getWholeText());
        sb.delete(offset, sb.length() - count);
        text(sb.toString());
    }

    @Override
    public void replaceData(int offset, int count, String data) {
        val sb = new StringBuilder(getWholeText());
        sb.replace(offset, sb.length() - count, data);
        text(sb.toString());
    }

    // child node

    @Override
    public void before(Object... nodes) {
    }

    @Override
    public void after(Object... nodes) {
    }

    @Override
    public void replaceWith(Object... nodes) {
    }

    // slotable


    @Override
    public HTMLSlotElement assignedSlot() {
        return null;
    }

    // non doc type child node
    
    @Override
    public Element previousElementSibling() {
        return nonDocumentTypeChildNodeMixin.previousElementSibling();
    }

    @Override
    public Element nextElementSibling() {
        return nonDocumentTypeChildNodeMixin.nextElementSibling();
    }

    @Override
    public @DOMString Attribute<String> textContent() {
        return new Attribute<String>() {

            @Override
            public String get() {
                return getWholeText();
            }

            @Override
            public void set(String s) {
                if (s != null) {
                    text(s);
                } else {
                    text("");
                }
            }

        };
    }

    @Override
    public Node appendChild(Node node) {
        return null;
    }

    public String nodeName() {
        return "#text";
    }

    /**
     * Get the text content of this text node.
     *
     * @return Unencoded, normalised text.
     * @see TextImpl#getWholeText()
     */
    public String text() {
        return StringUtil.normaliseWhitespace(getWholeText());
    }

    /**
     * Set the text content of this text node.
     *
     * @param text unencoded text
     * @return this, for chaining
     */
    public TextImpl text(String text) {
        coreValue(text);
        return this;
    }

    /**
     * Get the (unencoded) text of this text node, including any newlines and spaces present in the original.
     *
     * @return text
     */
    public String getWholeText() {
        return coreValue();
    }

    /**
     * Test if this text node is blank -- that is, empty or only whitespace (including newlines).
     *
     * @return true if this document is empty or only whitespace, false if it contains any text content.
     */
    public boolean isBlank() {
        return StringUtil.isBlank(coreValue());
    }

    /**
     * Split this text node into two nodes at the specified string offset. After splitting, this node will contain the
     * original text up to the offset, and will have a new text node sibling containing the text after the offset.
     *
     * @param offset string offset point to split node at.
     * @return the newly created text node containing the text after the offset.
     */
    public TextImpl splitText(int offset) {
        final String text = coreValue();
        Validate.isTrue(offset >= 0, "Split offset must be not be negative");
        Validate.isTrue(offset < text.length(), "Split offset must not be greater than current text length");

        String head = text.substring(0, offset);
        String tail = text.substring(offset);
        text(head);
        TextImpl tailNode = new TextImpl(tail);
        if (parent() != null)
            parent().addChildren(siblingIndex() + 1, tailNode);

        return tailNode;
    }

    @Override
    protected void outerHtmlHead(Appendable accum, int depth, DocumentImpl.OutputSettings out) throws IOException {
        if (out.prettyPrint() && ((siblingIndex() == 0 && parentNode instanceof ElementImpl && ((ElementImpl) parentNode).tag().formatAsBlock() && !isBlank()) || (out.outline() && siblingNodes().size() > 0 && !isBlank())))
            indent(accum, depth, out);

        boolean normaliseWhite = out.prettyPrint() && parent() instanceof ElementImpl
                && !ElementImpl.preserveWhitespace(parent());
        Entities.escape(accum, coreValue(), out, false, normaliseWhite, false);
    }

    @Override
    protected void outerHtmlTail(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
    }

    @Override
    public String toString() {
        return outerHtml();
    }

    @Override
    public String attr(String key) {
        Validate.notNull(key);
        if (!hasAttributes()) {
            return key.equals(nodeName()) ? (String) value : EmptyString;
        }
        return super.attr(key);
    }
    
    /**
     * Create a new TextNode from HTML encoded (aka escaped) data.
     *
     * @param encodedText Text containing encoded HTML (e.g. &amp;lt;)
     * @param baseUri     Base uri
     * @return TextNode containing unencoded data (e.g. &lt;)
     * @deprecated use {@link TextImpl#createFromEncoded(String)} instead, as LeafNodes don't carry base URIs.
     */
    public static TextImpl createFromEncoded(String encodedText, String baseUri) {
        String text = Entities.unescape(encodedText);
        return new TextImpl(text);
    }

    /**
     * Create a new TextNode from HTML encoded (aka escaped) data.
     *
     * @param encodedText Text containing encoded HTML (e.g. &amp;lt;)
     * @return TextNode containing unencoded data (e.g. &lt;)
     */
    public static TextImpl createFromEncoded(String encodedText) {
        String text = Entities.unescape(encodedText);
        return new TextImpl(text);
    }

    static String normaliseWhitespace(String text) {
        text = StringUtil.normaliseWhitespace(text);
        return text;
    }

    static String stripLeadingWhitespace(String text) {
        return text.replaceFirst("^\\s+", "");
    }

    public static boolean lastCharIsWhitespace(StringBuilder sb) {
        return sb.length() != 0 && sb.charAt(sb.length() - 1) == ' ';
    }
    
    
}
