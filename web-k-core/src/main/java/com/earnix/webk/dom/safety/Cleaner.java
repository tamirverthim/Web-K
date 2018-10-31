package com.earnix.webk.dom.safety;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.helper.Validate;
import com.earnix.webk.dom.nodes.AttributeModel;
import com.earnix.webk.dom.nodes.AttributesModel;
import com.earnix.webk.dom.nodes.DataNodeModel;
import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.dom.nodes.TextNodeModel;
import com.earnix.webk.dom.parser.ParseErrorList;
import com.earnix.webk.dom.parser.Parser;
import com.earnix.webk.dom.parser.Tag;
import com.earnix.webk.dom.select.NodeTraversor;
import com.earnix.webk.dom.select.NodeVisitor;

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
    public DocumentModel clean(DocumentModel dirtyDocument) {
        Validate.notNull(dirtyDocument);

        DocumentModel clean = DocumentModel.createShell(dirtyDocument.baseUri());
        if (dirtyDocument.body() != null) // frameset documents won't have a body. the clean doc will have empty body.
            copySafeNodes(dirtyDocument.body(), clean.body());

        return clean;
    }

    /**
     * Determines if the input document <b>body</b>is valid, against the whitelist. It is considered valid if all the tags and attributes
     * in the input HTML are allowed by the whitelist, and that there is no content in the <code>head</code>.
     * <p>
     * This method can be used as a validator for user input. An invalid document will still be cleaned successfully
     * using the {@link #clean(DocumentModel)} document. If using as a validator, it is recommended to still clean the document
     * to ensure enforced attributes are set correctly, and that the output is tidied.
     * </p>
     *
     * @param dirtyDocument document to test
     * @return true if no tags or attributes need to be removed; false if they do
     */
    public boolean isValid(DocumentModel dirtyDocument) {
        Validate.notNull(dirtyDocument);

        DocumentModel clean = DocumentModel.createShell(dirtyDocument.baseUri());
        int numDiscarded = copySafeNodes(dirtyDocument.body(), clean.body());
        return numDiscarded == 0
                && dirtyDocument.head().childNodes().size() == 0; // because we only look at the body, but we start from a shell, make sure there's nothing in the head
    }

    public boolean isValidBodyHtml(String bodyHtml) {
        DocumentModel clean = DocumentModel.createShell("");
        DocumentModel dirty = DocumentModel.createShell("");
        ParseErrorList errorList = ParseErrorList.tracking(1);
        List<NodeModel> nodes = Parser.parseFragment(bodyHtml, dirty.body(), "", errorList);
        dirty.body().insertChildren(0, nodes);
        int numDiscarded = copySafeNodes(dirty.body(), clean.body());
        return numDiscarded == 0 && errorList.size() == 0;
    }

    /**
     * Iterates the input and copies trusted nodes (tags, attributes, text) into the destination.
     */
    private final class CleaningVisitor implements NodeVisitor {
        private int numDiscarded = 0;
        private final ElementModel root;
        private ElementModel destination; // current element to append nodes to

        private CleaningVisitor(ElementModel root, ElementModel destination) {
            this.root = root;
            this.destination = destination;
        }

        public void head(NodeModel source, int depth) {
            if (source instanceof ElementModel) {
                ElementModel sourceEl = (ElementModel) source;

                if (whitelist.isSafeTag(sourceEl.tagName())) { // safe, clone and copy safe attrs
                    ElementMeta meta = createSafeElement(sourceEl);
                    ElementModel destChild = meta.el;
                    destination.appendChild(destChild);

                    numDiscarded += meta.numAttribsDiscarded;
                    destination = destChild;
                } else if (source != root) { // not a safe tag, so don't add. don't count root against discarded.
                    numDiscarded++;
                }
            } else if (source instanceof TextNodeModel) {
                TextNodeModel sourceText = (TextNodeModel) source;
                TextNodeModel destText = new TextNodeModel(sourceText.getWholeText());
                destination.appendChild(destText);
            } else if (source instanceof DataNodeModel && whitelist.isSafeTag(source.parent().nodeName())) {
                DataNodeModel sourceData = (DataNodeModel) source;
                DataNodeModel destData = new DataNodeModel(sourceData.getWholeData());
                destination.appendChild(destData);
            } else { // else, we don't care about comments, xml proc instructions, etc
                numDiscarded++;
            }
        }

        public void tail(NodeModel source, int depth) {
            if (source instanceof ElementModel && whitelist.isSafeTag(source.nodeName())) {
                destination = destination.parent(); // would have descended, so pop destination stack
            }
        }
    }

    private int copySafeNodes(ElementModel source, ElementModel dest) {
        CleaningVisitor cleaningVisitor = new CleaningVisitor(source, dest);
        NodeTraversor.traverse(cleaningVisitor, source);
        return cleaningVisitor.numDiscarded;
    }

    private ElementMeta createSafeElement(ElementModel sourceEl) {
        String sourceTag = sourceEl.tagName();
        AttributesModel destAttrs = new AttributesModel();
        ElementModel dest = new ElementModel(Tag.valueOf(sourceTag), sourceEl.baseUri(), destAttrs);
        int numDiscarded = 0;

        AttributesModel sourceAttrs = sourceEl.attributes();
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
        ElementModel el;
        int numAttribsDiscarded;

        ElementMeta(ElementModel el, int numAttribsDiscarded) {
            this.el = el;
            this.numAttribsDiscarded = numAttribsDiscarded;
        }
    }

}
