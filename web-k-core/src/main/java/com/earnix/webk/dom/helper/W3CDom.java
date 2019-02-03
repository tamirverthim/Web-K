package com.earnix.webk.dom.helper;

import com.earnix.webk.dom.internal.StringUtil;
import com.earnix.webk.dom.nodes.AttributeModel;
import com.earnix.webk.dom.nodes.AttributesModel;
import com.earnix.webk.dom.nodes.DataImpl;
import com.earnix.webk.dom.select.NodeTraversor;
import com.earnix.webk.dom.select.NodeVisitor;
import com.earnix.webk.script.impl.CommentImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.script.whatwg_dom.impl.DocumentImpl;
import com.earnix.webk.script.whatwg_dom.impl.TextImpl;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Stack;

/**
 * Helper class to transform a {@link DocumentImpl} to a {@link org.w3c.dom.Document org.w3c.dom.Document},
 * for integration with toolsets that use the W3C DOM.
 */
public class W3CDom {
    protected DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    /**
     * Convert a jsoup Document to a W3C Document.
     *
     * @param in jsoup doc
     * @return w3c doc
     */
    public Document fromJsoup(DocumentImpl in) {
        Validate.notNull(in);
        DocumentBuilder builder;
        try {
            //set the factory to be namespace-aware
            factory.setNamespaceAware(true);
            builder = factory.newDocumentBuilder();
            Document out = builder.newDocument();
            convert(in, out);
            return out;
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Converts a jsoup document into the provided W3C Document. If required, you can set options on the output document
     * before converting.
     *
     * @param in  jsoup doc
     * @param out w3c doc
     * @see com.earnix.webk.dom.helper.W3CDom#fromJsoup(DocumentImpl)
     */
    public void convert(DocumentImpl in, Document out) {
        if (!StringUtil.isBlank(in.getLocation()))
            out.setDocumentURI(in.getLocation());

        ElementImpl rootEl = in.child(0); // skip the #root node
        NodeTraversor.traverse(new W3CBuilder(out), rootEl);
    }

    /**
     * Implements the conversion by walking the input.
     */
    protected static class W3CBuilder implements NodeVisitor {
        private static final String xmlnsKey = "xmlns";
        private static final String xmlnsPrefix = "xmlns:";

        private final Document doc;
        private final Stack<HashMap<String, String>> namespacesStack = new Stack<>(); // stack of namespaces, prefix => urn
        private Element dest;

        public W3CBuilder(Document doc) {
            this.doc = doc;
            this.namespacesStack.push(new HashMap<String, String>());
        }

        public void head(NodeImpl source, int depth) {
            namespacesStack.push(new HashMap<>(namespacesStack.peek())); // inherit from above on the stack
            if (source instanceof ElementImpl) {
                ElementImpl sourceEl = (ElementImpl) source;

                String prefix = updateNamespaces(sourceEl);
                String namespace = namespacesStack.peek().get(prefix);
                String tagName = sourceEl.tagName();

                Element el = namespace == null && tagName.contains(":") ?
                        doc.createElementNS("", tagName) : // doesn't have a real namespace defined
                        doc.createElementNS(namespace, tagName);
                copyAttributes(sourceEl, el);
                if (dest == null) { // sets up the root
                    doc.appendChild(el);
                } else {
                    dest.appendChild(el);
                }
                dest = el; // descend
            } else if (source instanceof TextImpl) {
                TextImpl sourceText = (TextImpl) source;
                Text text = doc.createTextNode(sourceText.getWholeText());
                dest.appendChild(text);
            } else if (source instanceof CommentImpl) {
                CommentImpl sourceComment = (CommentImpl) source;
                Comment comment = doc.createComment(sourceComment.getData());
                dest.appendChild(comment);
            } else if (source instanceof DataImpl) {
                DataImpl sourceData = (DataImpl) source;
                Text node = doc.createTextNode(sourceData.getWholeData());
                dest.appendChild(node);
            } else {
                // unhandled
            }
        }

        public void tail(NodeImpl source, int depth) {
            if (source instanceof ElementImpl && dest.getParentNode() instanceof Element) {
                dest = (Element) dest.getParentNode(); // undescend. cromulent.
            }
            namespacesStack.pop();
        }

        private void copyAttributes(NodeImpl source, Element el) {
            for (AttributeModel attribute : source.getAttributes()) {
                // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
                String key = attribute.getKey().replaceAll("[^-a-zA-Z0-9_:.]", "");
                if (key.matches("[a-zA-Z_:][-a-zA-Z0-9_:.]*"))
                    el.setAttribute(key, attribute.getValue());
            }
        }

        /**
         * Finds any namespaces defined in this element. Returns any tag prefix.
         */
        private String updateNamespaces(ElementImpl el) {
            // scan the element for namespace declarations
            // like: xmlns="blah" or xmlns:prefix="blah"
            AttributesModel attributes = el.getAttributes();
            for (AttributeModel attr : attributes) {
                String key = attr.getKey();
                String prefix;
                if (key.equals(xmlnsKey)) {
                    prefix = "";
                } else if (key.startsWith(xmlnsPrefix)) {
                    prefix = key.substring(xmlnsPrefix.length());
                } else {
                    continue;
                }
                namespacesStack.peek().put(prefix, attr.getValue());
            }

            // get the element prefix if any
            int pos = el.tagName().indexOf(":");
            return pos > 0 ? el.tagName().substring(0, pos) : "";
        }

    }

    /**
     * Serialize a W3C document to a String.
     *
     * @param doc Document
     * @return Document as string
     */
    public String asString(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
    }
}
