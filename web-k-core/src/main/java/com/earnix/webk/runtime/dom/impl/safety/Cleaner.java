package com.earnix.webk.runtime.dom.impl.safety;

import com.earnix.webk.runtime.dom.impl.Jsoup;
import com.earnix.webk.runtime.dom.impl.helper.Validate;
import com.earnix.webk.runtime.dom.impl.nodes.AttributeModel;
import com.earnix.webk.runtime.dom.impl.nodes.AttributesModel;
import com.earnix.webk.runtime.dom.impl.nodes.DataImpl;
import com.earnix.webk.runtime.dom.impl.parser.ParseErrorList;
import com.earnix.webk.runtime.dom.impl.parser.Parser;
import com.earnix.webk.runtime.dom.impl.parser.Tag;
import com.earnix.webk.runtime.dom.impl.select.NodeTraversor;
import com.earnix.webk.runtime.dom.impl.select.NodeVisitor;
import com.earnix.webk.runtime.dom.impl.ElementImpl;
import com.earnix.webk.runtime.dom.impl.NodeImpl;
import com.earnix.webk.runtime.html.impl.DocumentImpl;
import com.earnix.webk.runtime.dom.impl.TextImpl;

import java.util.List;


/**
 * The whitelist based HTML cleaner. Use to ensure that end-user provided HTML contains only the elements and attributes
 * that you are expecting; no junk, and no cross-site scripting attacks!
 * <p>
 * The HTML cleaner parses the input as HTML and then runs it through a white-list, so the output HTML can only contain
 * HTML that is allowed by the whitelist.
 * </p>
 * <p>
 * It is assumed that the input HTML is a body fragment; the clean methods only pull from the source's body, and the
 * canned white-lists only allow body contained tags.
 * </p>
 * <p>
 * Rather than interacting directly with a Cleaner object, generally see the {@code clean} methods in {@link Jsoup}.
 * </p>
 */
public class Cleaner {
    private Whitelist whitelist;

    /**
     * Create a new cleaner, that sanitizes documents using the supplied whitelist.
     *
     * @param whitelist white-list to clean with
     */
    public Cleaner(Whitelist whitelist) {
        Validate.notNull(whitelist);
        this.whitelist = whitelist;
    }

    /**
     * Creates a new, clean document, from the original dirty document, containing only elements allowed by the whitelist.
     * The original document is not modified. Only elements from the dirt document's <code>body</code> are used.
     *
     * @param dirtyDocument Untrusted base document to clean.
     * @return cleaned document.
     */
    public DocumentImpl clean(DocumentImpl dirtyDocument) {
        Validate.notNull(dirtyDocument);

        DocumentImpl clean = DocumentImpl.createShell(dirtyDocument.baseUri());
        if (dirtyDocument.body() != null) // frameset documents won't have a body. the clean doc will have empty body.
            copySafeNodes(dirtyDocument.getBody(), clean.getBody());

        return clean;
    }

    /**
     * Determines if the input document <b>body</b>is valid, against the whitelist. It is considered valid if all the tags and attributes
     * in the input HTML are allowed by the whitelist, and that there is no content in the <code>head</code>.
     * <p>
     * This method can be used as a validator for user input. An invalid document will still be cleaned successfully
     * using the {@link #clean(DocumentImpl)} document. If using as a validator, it is recommended to still clean the document
     * to ensure enforced attributes are set correctly, and that the output is tidied.
     * </p>
     *
     * @param dirtyDocument document to test
     * @return true if no tags or attributes need to be removed; false if they do
     */
    public boolean isValid(DocumentImpl dirtyDocument) {
        Validate.notNull(dirtyDocument);

        DocumentImpl clean = DocumentImpl.createShell(dirtyDocument.baseUri());
        int numDiscarded = copySafeNodes(dirtyDocument.getBody(), clean.getBody());
        return numDiscarded == 0
                && dirtyDocument.getHead().getChildNodes().size() == 0; // because we only look at the body, but we start from a shell, make sure there's nothing in the head
    }

    public boolean isValidBodyHtml(String bodyHtml) {
        DocumentImpl clean = DocumentImpl.createShell("");
        DocumentImpl dirty = DocumentImpl.createShell("");
        ParseErrorList errorList = ParseErrorList.tracking(1);
        List<NodeImpl> nodes = Parser.parseFragment(bodyHtml, dirty.getBody(), "", errorList);
        dirty.getBody().insertChildren(0, nodes);
        int numDiscarded = copySafeNodes(dirty.getBody(), clean.getBody());
        return numDiscarded == 0 && errorList.size() == 0;
    }

    /**
     * Iterates the input and copies trusted nodes (tags, attributes, text) into the destination.
     */
    private final class CleaningVisitor implements NodeVisitor {
        private int numDiscarded = 0;
        private final ElementImpl root;
        private ElementImpl destination; // current element to append nodes to

        private CleaningVisitor(ElementImpl root, ElementImpl destination) {
            this.root = root;
            this.destination = destination;
        }

        public void head(NodeImpl source, int depth) {
            if (source instanceof ElementImpl) {
                ElementImpl sourceEl = (ElementImpl) source;

                if (whitelist.isSafeTag(sourceEl.tagName())) { // safe, clone and copy safe attrs
                    ElementMeta meta = createSafeElement(sourceEl);
                    ElementImpl destChild = meta.el;
                    destination.appendChild(destChild);

                    numDiscarded += meta.numAttribsDiscarded;
                    destination = destChild;
                } else if (source != root) { // not a safe tag, so don't add. don't count root against discarded.
                    numDiscarded++;
                }
            } else if (source instanceof TextImpl) {
                TextImpl sourceText = (TextImpl) source;
                TextImpl destText = new TextImpl(sourceText.getWholeText());
                destination.appendChild(destText);
            } else if (source instanceof DataImpl && whitelist.isSafeTag(source.parent().nodeName())) {
                DataImpl sourceData = (DataImpl) source;
                DataImpl destData = new DataImpl(sourceData.getWholeData());
                destination.appendChild(destData);
            } else { // else, we don't care about comments, xml proc instructions, etc
                numDiscarded++;
            }
        }

        public void tail(NodeImpl source, int depth) {
            if (source instanceof ElementImpl && whitelist.isSafeTag(source.nodeName())) {
                destination = destination.parent(); // would have descended, so pop destination stack
            }
        }
    }

    private int copySafeNodes(ElementImpl source, ElementImpl dest) {
        CleaningVisitor cleaningVisitor = new CleaningVisitor(source, dest);
        NodeTraversor.traverse(cleaningVisitor, source);
        return cleaningVisitor.numDiscarded;
    }

    private ElementMeta createSafeElement(ElementImpl sourceEl) {
        String sourceTag = sourceEl.tagName();
        AttributesModel destAttrs = new AttributesModel();
        ElementImpl dest = new ElementImpl(Tag.valueOf(sourceTag), sourceEl.baseUri(), destAttrs);
        int numDiscarded = 0;

        AttributesModel sourceAttrs = sourceEl.getAttributes();
        for (AttributeModel sourceAttr : sourceAttrs) {
            if (whitelist.isSafeAttribute(sourceTag, sourceEl, sourceAttr))
                destAttrs.put(sourceAttr);
            else
                numDiscarded++;
        }
        AttributesModel enforcedAttrs = whitelist.getEnforcedAttributes(sourceTag);
        destAttrs.addAll(enforcedAttrs);

        return new ElementMeta(dest, numDiscarded);
    }

    private static class ElementMeta {
        ElementImpl el;
        int numAttribsDiscarded;

        ElementMeta(ElementImpl el, int numAttribsDiscarded) {
            this.el = el;
            this.numAttribsDiscarded = numAttribsDiscarded;
        }
    }

}
