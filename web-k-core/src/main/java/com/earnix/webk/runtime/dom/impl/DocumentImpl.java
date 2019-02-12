package com.earnix.webk.runtime.dom.impl;

import com.earnix.webk.runtime.dom.impl.helper.Validate;
import com.earnix.webk.runtime.dom.impl.internal.StringUtil;
import com.earnix.webk.runtime.dom.impl.nodes.Entities;
import com.earnix.webk.runtime.dom.impl.nodes.XmlDeclarationModel;
import com.earnix.webk.runtime.dom.impl.parser.ParseSettings;
import com.earnix.webk.runtime.dom.impl.parser.Parser;
import com.earnix.webk.runtime.dom.impl.parser.Tag;
import com.earnix.webk.runtime.dom.impl.parser.TreeBuilder;
import com.earnix.webk.runtime.dom.impl.select.Elements;
import com.earnix.webk.runtime.ScriptContext;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.USVString;
import com.earnix.webk.runtime.dom.Attr;
import com.earnix.webk.runtime.dom.CDATASection;
import com.earnix.webk.runtime.dom.Comment;
import com.earnix.webk.runtime.dom.DOMImplementation;
import com.earnix.webk.runtime.dom.Document;
import com.earnix.webk.runtime.dom.DocumentFragment;
import com.earnix.webk.runtime.dom.DocumentType;
import com.earnix.webk.runtime.dom.Element;
import com.earnix.webk.runtime.dom.Event;
import com.earnix.webk.runtime.dom.HTMLCollection;
import com.earnix.webk.runtime.dom.Node;
import com.earnix.webk.runtime.dom.NodeFilter;
import com.earnix.webk.runtime.dom.NodeIterator;
import com.earnix.webk.runtime.dom.NodeList;
import com.earnix.webk.runtime.dom.ProcessingInstruction;
import com.earnix.webk.runtime.dom.Range;
import com.earnix.webk.runtime.dom.Text;
import com.earnix.webk.runtime.dom.TreeWalker;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentImpl extends ElementImpl implements Document {

    @Setter ScriptContext scriptContext;
    DOMImplementation implementation = new DOMImplementationImpl();
    private DocumentImpl.OutputSettings outputSettings = new DocumentImpl.OutputSettings();
    private Parser parser; // the parser used to parse this document
    private DocumentImpl.QuirksMode quirksMode = DocumentImpl.QuirksMode.noQuirks;
    String location;
    private boolean updateMetaCharset = false;
    
    @Override
    public ScriptContext scriptContext() {
        return scriptContext;
    }

    @Override
    public DOMImplementation implementation() {
        return implementation;
    }

    @Override
    public @USVString
    String URL() {
        return location;
    }

    @Override
    public @USVString
    String documentURI() {
        return location;
    }

    @Override
    public @USVString
    String origin() {
        return null;
    }

    @Override
    public @DOMString String compatMode() {
        return "CSS1Compat";
    }

    @Override
    public @DOMString String characterSet() {
        return "UTF-8";
    }

    @Override
    public @DOMString String charset() {
        return getCharset().displayName();
    }

    @Override
    public @DOMString String inputEncoding() {
        return null;
    }

    @Override
    public @DOMString String contentType() {
        return null;
    }

    @Override
    public DocumentType doctype() {
        return null;
    }

    @Override
    public Element documentElement() {
        return this;
    }

    @Override
    public HTMLCollection getElementsByTagName(String qualifiedName) {
        val elements = getElementsByTag(qualifiedName.toString());
        return new HTMLCollectionImpl(elements);
    }

    @Override
    public HTMLCollection getElementsByTagNameNS(String namespace, String localName) {
        return getElementsByTagName(localName);
    }

    @Override
    public HTMLCollection getElementsByClassName(String classNames) {
        val modelElements = getElementsByClass(classNames.toString());
        return new HTMLCollectionImpl(modelElements);
    }

    @Override
    public Element createElement(String localName, Object options) {
        ElementImpl element = createElement(localName);
        // attaching script context 
        element.setScriptContext(scriptContext);
        return element;
    }

    @Override
    public Element createElementNS(String namespace, String qualifiedName, Object options) {
        return createElement(qualifiedName, options);
    }

    @Override
    public DocumentFragment createDocumentFragment() {
        return null;
    }

    @Override
    public Text createTextNode(String data) {
        return new TextImpl(data);
    }

    @Override
    public CDATASection createCDATASection(String data) {
        return new CDATASectionImpl(data);
    }

    @Override
    public Comment createComment(String data) {
        return new CommentImpl(data);
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(String target, String data) {
        return null;
    }

    @Override
    public Node importNode(Node node, boolean deep) {
        return null;
    }

    @Override
    public Node adoptNode(Node node) {
        return null;
    }

    @Override
    public Attr createAttribute(String localName) {
        return null;
    }

    @Override
    public Attr createAttributeNS(String namespace, String qualifiedName) {
        return null;
    }

    @Override
    public Event createEvent(String inter_face) {
        return null;
    }

    @Override
    public Range createRange() {
        return null;
    }

    @Override
    public NodeIterator createNodeIterator(Node root, long whatToShow, NodeFilter filter) {
        return null;
    }

    @Override
    public TreeWalker createTreeWalker(Node root, long whatToShow, NodeFilter filter) {
        return null;
    }

    @Override
    public HTMLCollection children() {
        return new HTMLCollectionImpl(getChildren());
    }

    @Override
    public Element firstElementChild() {
        return getChildren().first();
    }

    @Override
    public Element lastElementChild() {
        return getChildren().last();
    }

    @Override
    public Integer childElementCount() {
        return getChildren().size();
    }

    @Override
    public void prepend(Object... nodes) {
        
    }

    @Override
    public void append(Object... nodes) {

    }

    @Override
    public Element querySelector(String selectors) {
        val selected = select(selectors);
        if (selected.size() > 0) {
            return selected.first();
        }
        return null;
    }

    @Override
    public NodeList querySelectorAll(String selectors) {
        val selected = select(selectors);
        val nodes = new ArrayList<NodeImpl>(selected);
        return new NodeListImpl(nodes);
    }
    
    // region model
    
    /**
     * Create a new, empty Document.
     *
     * @param baseUri base URI of document
     * @see Jsoup#parse
     * @see #createShell
     */
    public DocumentImpl(String baseUri) {
        super(Tag.valueOf("#root", ParseSettings.htmlDefault), baseUri);
        this.location = baseUri;
    }

    /**
     * Create a valid, empty shell of a document, suitable for adding more elements to.
     *
     * @param baseUri baseUri of document
     * @return document with html, head, and body elements.
     */
    public static com.earnix.webk.runtime.html.impl.DocumentImpl createShell(String baseUri) {
        Validate.notNull(baseUri);

        com.earnix.webk.runtime.html.impl.DocumentImpl doc = new com.earnix.webk.runtime.html.impl.DocumentImpl(baseUri);
        ((DocumentImpl)doc).parser = doc.parser();
        ElementImpl html = doc.appendElement("html");
        html.appendElement("head");
        html.appendElement("body");

        return doc;
    }

    /**
     * Get the URL this Document was parsed from. If the starting URL is a redirect,
     * this will return the final URL from which the document was served from.
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Accessor to the document's {@code head} element.
     *
     * @return {@code head}
     */
    public ElementImpl getHead() {
        return findFirstElementByTagName("head", this);
    }

    /**
     * Accessor to the document's {@code body} element.
     *
     * @return {@code body}
     */
    public ElementImpl getBody() {
        return findFirstElementByTagName("body", this);
    }

    /**
     * Get the string contents of the document's {@code title} element.
     *
     * @return Trimmed title, or empty string if none set.
     */
    public String getTitle() {
        // title is a preserve whitespace tag (for document output), but normalised here
        ElementImpl titleEl = getElementsByTag("title").first();
        return titleEl != null ? StringUtil.normaliseWhitespace(titleEl.text()).trim() : "";
    }

    /**
     * Set the document's {@code title} element. Updates the existing element, or adds {@code title} to {@code head} if
     * not present
     *
     * @param title string to set as title
     */
    public void setTitle(String title) {
        Validate.notNull(title);
        ElementImpl titleEl = getElementsByTag("title").first();
        if (titleEl == null) { // add to head
            getHead().appendElement("title").text(title);
        } else {
            titleEl.text(title);
        }
    }

    /**
     * Create a new Element, with this document's base uri. Does not make the new element a child of this document.
     *
     * @param tagName element tag name (e.g. {@code a})
     * @return new element
     */
    public ElementImpl createElement(String tagName) {
        return TreeBuilder.createElement(Tag.valueOf(tagName, ParseSettings.preserveCase), this.baseUri());
    }

    /**
     * Normalise the document. This happens after the parse phase so generally does not need to be called.
     * Moves any text content that is not in the body element into the body.
     *
     * @return this document after normalisation
     */
    public DocumentImpl normalise() {
        ElementImpl htmlEl = findFirstElementByTagName("html", this);
        if (htmlEl == null)
            htmlEl = appendElement("html");
        if (getHead() == null)
            htmlEl.prependElement("head");
        if (getBody() == null)
            htmlEl.appendElement("body");

        // pull text nodes out of root, html, and head els, and push into body. non-text nodes are already taken care
        // of. do in inverse order to maintain text order.
        normaliseTextNodes(getHead());
        normaliseTextNodes(htmlEl);
        normaliseTextNodes(this);

        normaliseStructure("head", htmlEl);
        normaliseStructure("body", htmlEl);

        ensureMetaCharsetElement();

        return this;
    }

    // does not recurse.
    private void normaliseTextNodes(ElementImpl element) {
        List<NodeImpl> toMove = new ArrayList<>();
        for (NodeImpl node : element.childNodes) {
            if (node instanceof TextImpl) {
                TextImpl tn = (TextImpl) node;
                if (!tn.isBlank())
                    toMove.add(tn);
            }
        }

        for (int i = toMove.size() - 1; i >= 0; i--) {
            NodeImpl node = toMove.get(i);
            element.removeChild(node);
            getBody().prependChild(new TextImpl(" "));
            getBody().prependChild(node);
        }
    }

    // merge multiple <head> or <body> contents into one, delete the remainder, and ensure they are owned by <html>
    private void normaliseStructure(String tag, ElementImpl htmlEl) {
        Elements elements = this.getElementsByTag(tag);
        ElementImpl master = elements.first(); // will always be available as created above if not existent
        if (elements.size() > 1) { // dupes, move contents to master
            List<NodeImpl> toMove = new ArrayList<>();
            for (int i = 1; i < elements.size(); i++) {
                NodeImpl dupe = elements.get(i);
                toMove.addAll(dupe.ensureChildNodes());
                dupe.remove();
            }

            for (NodeImpl dupe : toMove)
                master.appendChild(dupe);
        }
        // ensure parented by <html>
        if (!master.parent().equals(htmlEl)) {
            htmlEl.appendChild(master); // includes remove()            
        }
    }

    // fast method to get first by tag name, used for html, head, body finders
    private ElementImpl findFirstElementByTagName(String tag, NodeImpl node) {
        if (node.nodeName().equals(tag))
            return (ElementImpl) node;
        else {
            int size = node.childNodeSize();
            for (int i = 0; i < size; i++) {
                ElementImpl found = findFirstElementByTagName(tag, node.childNode(i));
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    @Override
    public String outerHtml() {
        return super.html(); // no outer wrapper tag
    }

    /**
     * Set the text of the {@code body} of this document. Any existing nodes within the body will be cleared.
     *
     * @param text unencoded text
     * @return this document
     */
    @Override
    public ElementImpl text(String text) {
        getBody().text(text); // overridden to not nuke doc structure
        return this;
    }

    @Override
    public String nodeName() {
        return "#document";
    }

    /**
     * Sets the charset used in this document. This method is equivalent
     * to {@link OutputSettings#charset(java.nio.charset.Charset)
     * OutputSettings.charset(Charset)} but in addition it updates the
     * charset / encoding element within the document.
     *
     * <p>This enables
     * {@link #updateMetaCharsetElement(boolean) meta charset update}.</p>
     *
     * <p>If there's no element with charset / encoding information yet it will
     * be created. Obsolete charset / encoding definitions are removed!</p>
     *
     * <p><b>Elements used:</b></p>
     *
     * <ul>
     * <li><b>Html:</b> <i>&lt;meta charset="CHARSET"&gt;</i></li>
     * <li><b>Xml:</b> <i>&lt;?xml version="1.0" encoding="CHARSET"&gt;</i></li>
     * </ul>
     *
     * @param charset Charset
     * @see #updateMetaCharsetElement(boolean)
     * @see OutputSettings#charset(java.nio.charset.Charset)
     */
    public void charset(Charset charset) {
        updateMetaCharsetElement(true);
        outputSettings.charset(charset);
        ensureMetaCharsetElement();
    }

    /**
     * Returns the charset used in this document. This method is equivalent
     * to {@link OutputSettings#charset()}.
     *
     * @return Current Charset
     * @see OutputSettings#charset()
     */
    public Charset getCharset() {
        return outputSettings.charset();
    }

    /**
     * Sets whether the element with charset information in this document is
     * updated on changes through {@link #charset(java.nio.charset.Charset)
     * Document.charset(Charset)} or not.
     *
     * <p>If set to <tt>false</tt> <i>(default)</i> there are no elements
     * modified.</p>
     *
     * @param update If <tt>true</tt> the element updated on charset
     *               changes, <tt>false</tt> if not
     * @see #charset(java.nio.charset.Charset)
     */
    public void updateMetaCharsetElement(boolean update) {
        this.updateMetaCharset = update;
    }

    /**
     * Returns whether the element with charset information in this document is
     * updated on changes through {@link #charset(java.nio.charset.Charset)
     * Document.charset(Charset)} or not.
     *
     * @return Returns <tt>true</tt> if the element is updated on charset
     * changes, <tt>false</tt> if not
     */
    public boolean updateMetaCharsetElement() {
        return updateMetaCharset;
    }

    @Override
    public DocumentImpl clone() {
        DocumentImpl clone = (DocumentImpl) super.clone();
        clone.outputSettings = this.outputSettings.clone();
        return clone;
    }

    /**
     * Ensures a meta charset (html) or xml declaration (xml) with the current
     * encoding used. This only applies with
     * {@link #updateMetaCharsetElement(boolean) updateMetaCharset} set to
     * <tt>true</tt>, otherwise this method does nothing.
     *
     * <ul>
     * <li>An existing element gets updated with the current charset</li>
     * <li>If there's no element yet it will be inserted</li>
     * <li>Obsolete elements are removed</li>
     * </ul>
     *
     * <p><b>Elements used:</b></p>
     *
     * <ul>
     * <li><b>Html:</b> <i>&lt;meta charset="CHARSET"&gt;</i></li>
     * <li><b>Xml:</b> <i>&lt;?xml version="1.0" encoding="CHARSET"&gt;</i></li>
     * </ul>
     */
    private void ensureMetaCharsetElement() {
        if (updateMetaCharset) {
            OutputSettings.Syntax syntax = outputSettings().syntax();

            if (syntax == OutputSettings.Syntax.html) {
                ElementImpl metaCharset = select("meta[charset]").first();

                if (metaCharset != null) {
                    metaCharset.attr("charset", getCharset().displayName());
                } else {
                    ElementImpl head = getHead();

                    if (head != null) {
                        head.appendElement("meta").attr("charset", getCharset().displayName());
                    }
                }

                // Remove obsolete elements
                select("meta[name=charset]").remove();
            } else if (syntax == OutputSettings.Syntax.xml) {
                NodeImpl node = getChildNodes().get(0);

                if (node instanceof XmlDeclarationModel) {
                    XmlDeclarationModel decl = (XmlDeclarationModel) node;

                    if (decl.name().equals("xml")) {
                        decl.attr("encoding", getCharset().displayName());

                        final String version = decl.attr("version");

                        if (version != null) {
                            decl.attr("version", "1.0");
                        }
                    } else {
                        decl = new XmlDeclarationModel("xml", false);
                        decl.attr("version", "1.0");
                        decl.attr("encoding", getCharset().displayName());

                        prependChild(decl);
                    }
                } else {
                    XmlDeclarationModel decl = new XmlDeclarationModel("xml", false);
                    decl.attr("version", "1.0");
                    decl.attr("encoding", getCharset().displayName());

                    prependChild(decl);
                }
            }
        }
    }


    /**
     * A Document's output settings control the form of the text() and html() methods.
     */
    public static class OutputSettings implements Cloneable {
        /**
         * The output serialization syntax.
         */
        public enum Syntax {
            html, xml
        }

        private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
        private Charset charset;
        private ThreadLocal<CharsetEncoder> encoderThreadLocal = new ThreadLocal<>(); // initialized by start of OuterHtmlVisitor
        public Entities.CoreCharset coreCharset; // fast encoders for ascii and utf8

        private boolean prettyPrint = true;
        private boolean outline = false;
        private int indentAmount = 1;
        private Syntax syntax = Syntax.html;

        public OutputSettings() {
            charset(Charset.forName("UTF8"));
        }

        /**
         * Get the document's current HTML escape mode: <code>base</code>, which provides a limited set of named HTML
         * entities and escapes other characters as numbered entities for maximum compatibility; or <code>extended</code>,
         * which uses the complete set of HTML named entities.
         * <p>
         * The default escape mode is <code>base</code>.
         *
         * @return the document's current escape mode
         */
        public Entities.EscapeMode escapeMode() {
            return escapeMode;
        }

        /**
         * Set the document's escape mode, which determines how characters are escaped when the output character set
         * does not support a given character:- using either a named or a numbered escape.
         *
         * @param escapeMode the new escape mode to use
         * @return the document's output settings, for chaining
         */
        public OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
            this.escapeMode = escapeMode;
            return this;
        }

        /**
         * Get the document's current output charset, which is used to control which characters are escaped when
         * generating HTML (via the <code>html()</code> methods), and which are kept intact.
         * <p>
         * Where possible (when parsing from a URL or File), the document's output charset is automatically set to the
         * input charset. Otherwise, it defaults to UTF-8.
         *
         * @return the document's current charset.
         */
        public Charset charset() {
            return charset;
        }

        /**
         * Update the document's output charset.
         *
         * @param charset the new charset to use.
         * @return the document's output settings, for chaining
         */
        public OutputSettings charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * Update the document's output charset.
         *
         * @param charset the new charset (by name) to use.
         * @return the document's output settings, for chaining
         */
        public OutputSettings charset(String charset) {
            charset(Charset.forName(charset));
            return this;
        }

        public CharsetEncoder prepareEncoder() {
            // created at start of OuterHtmlVisitor so each pass has own encoder, so OutputSettings can be shared among threads
            CharsetEncoder encoder = charset.newEncoder();
            encoderThreadLocal.set(encoder);
            coreCharset = Entities.CoreCharset.byName(encoder.charset().name());
            return encoder;
        }

        public CharsetEncoder encoder() {
            CharsetEncoder encoder = encoderThreadLocal.get();
            return encoder != null ? encoder : prepareEncoder();
        }

        /**
         * Get the document's current output syntax.
         *
         * @return current syntax
         */
        public Syntax syntax() {
            return syntax;
        }

        /**
         * Set the document's output syntax. Either {@code html}, with empty tags and boolean attributes (etc), or
         * {@code xml}, with self-closing tags.
         *
         * @param syntax serialization syntax
         * @return the document's output settings, for chaining
         */
        public OutputSettings syntax(Syntax syntax) {
            this.syntax = syntax;
            return this;
        }

        /**
         * Get if pretty printing is enabled. Default is true. If disabled, the HTML output methods will not re-format
         * the output, and the output will generally look like the input.
         *
         * @return if pretty printing is enabled.
         */
        public boolean prettyPrint() {
            return prettyPrint;
        }

        /**
         * Enable or disable pretty printing.
         *
         * @param pretty new pretty print setting
         * @return this, for chaining
         */
        public OutputSettings prettyPrint(boolean pretty) {
            prettyPrint = pretty;
            return this;
        }

        /**
         * Get if outline mode is enabled. Default is false. If enabled, the HTML output methods will consider
         * all tags as block.
         *
         * @return if outline mode is enabled.
         */
        public boolean outline() {
            return outline;
        }

        /**
         * Enable or disable HTML outline mode.
         *
         * @param outlineMode new outline setting
         * @return this, for chaining
         */
        public OutputSettings outline(boolean outlineMode) {
            outline = outlineMode;
            return this;
        }

        /**
         * Get the current tag indent amount, used when pretty printing.
         *
         * @return the current indent amount
         */
        public int indentAmount() {
            return indentAmount;
        }

        /**
         * Set the indent amount for pretty printing
         *
         * @param indentAmount number of spaces to use for indenting each level. Must be {@literal >=} 0.
         * @return this, for chaining
         */
        public OutputSettings indentAmount(int indentAmount) {
            Validate.isTrue(indentAmount >= 0);
            this.indentAmount = indentAmount;
            return this;
        }

        @Override
        public OutputSettings clone() {
            OutputSettings clone;
            try {
                clone = (OutputSettings) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            clone.charset(charset.name()); // new charset and charset encoder
            clone.escapeMode = Entities.EscapeMode.valueOf(escapeMode.name());
            // indentAmount, prettyPrint are primitives so object.clone() will handle
            return clone;
        }
    }

    /**
     * Get the document's current output settings.
     *
     * @return the document's current output settings.
     */
    public OutputSettings outputSettings() {
        return outputSettings;
    }

    /**
     * Set the document's output settings.
     *
     * @param outputSettings new output settings.
     * @return this document, for chaining.
     */
    public DocumentImpl outputSettings(OutputSettings outputSettings) {
        Validate.notNull(outputSettings);
        this.outputSettings = outputSettings;
        return this;
    }

    public enum QuirksMode {
        noQuirks, quirks, limitedQuirks
    }

    public QuirksMode quirksMode() {
        return quirksMode;
    }

    public DocumentImpl quirksMode(QuirksMode quirksMode) {
        this.quirksMode = quirksMode;
        return this;
    }

    /**
     * Get the parser that was used to parse this document.
     *
     * @return the parser
     */
    public Parser parser() {
        return parser;
    }

    /**
     * Set the parser used to create this document. This parser is then used when further parsing within this document
     * is required.
     *
     * @param parser the configured parser to use when further parsing is required for this document.
     * @return this document, for chaining.
     */
    public DocumentImpl parser(Parser parser) {
        this.parser = parser;
        return this;
    }
    
    // endregion
}

