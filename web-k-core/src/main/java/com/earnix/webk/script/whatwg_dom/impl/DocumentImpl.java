package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.CommentModel;
import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.dom.nodes.TextNodeModel;
import com.earnix.webk.script.impl.CommentImpl;
import com.earnix.webk.script.impl.DOMImplementationImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.NodeListImpl;
import com.earnix.webk.script.whatwg_dom.impl.ScriptDOMFactory;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.whatwg_dom.Attr;
import com.earnix.webk.script.whatwg_dom.CDATASection;
import com.earnix.webk.script.whatwg_dom.Comment;
import com.earnix.webk.script.whatwg_dom.DOMImplementation;
import com.earnix.webk.script.whatwg_dom.Document;
import com.earnix.webk.script.whatwg_dom.DocumentFragment;
import com.earnix.webk.script.whatwg_dom.DocumentType;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.HTMLCollection;
import com.earnix.webk.script.whatwg_dom.Node;
import com.earnix.webk.script.whatwg_dom.NodeFilter;
import com.earnix.webk.script.whatwg_dom.NodeIterator;
import com.earnix.webk.script.whatwg_dom.NodeList;
import com.earnix.webk.script.whatwg_dom.ProcessingInstruction;
import com.earnix.webk.script.whatwg_dom.Range;
import com.earnix.webk.script.whatwg_dom.Text;
import com.earnix.webk.script.whatwg_dom.TreeWalker;
import com.earnix.webk.script.whatwg_dom.impl.HTMLCollectionImpl;
import com.earnix.webk.script.whatwg_dom.impl.TextImpl;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.ArrayList;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentImpl implements Document {

    DocumentModel document;
    DOMImplementation implementation = new DOMImplementationImpl();
    String url;

    public DocumentImpl(DocumentModel model, String url ) {
        this.document = model;
        this.url = url;
    }

    @Override
    public DOMImplementation implementation() {
        return implementation;
    }

    @Override
    public @USVString
    String URL() {
        return url;
    }

    @Override
    public @USVString
    String documentURI() {
        return url;
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
        return "UTF8";
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
        return (Element) ScriptDOMFactory.get(document);
    }

    @Override
    public HTMLCollection getElementsByTagName(String qualifiedName) {
        val elements = document.getElementsByTag(qualifiedName.toString());
        return new HTMLCollectionImpl(elements);
    }

    @Override
    public HTMLCollection getElementsByTagNameNS(String namespace, String localName) {
        return getElementsByTagName(localName);
    }

    @Override
    public HTMLCollection getElementsByClassName(String classNames) {
        val modelElements = document.getElementsByClass(classNames.toString());
        return new HTMLCollectionImpl(modelElements);
    }

    @Override
    public Element createElement(String localName, Object options) {
        return ScriptDOMFactory.getElement(new ElementModel(localName));
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
        TextNodeModel textNode = new TextNodeModel(data);
        val result = new TextImpl(textNode);
        ScriptDOMFactory.put(textNode, result);
        return result;
    }

    @Override
    public CDATASection createCDATASection(String data) {
        return new CDATASectionImpl(new TextNodeModel(data));
    }

    @Override
    public Comment createComment(String data) {
        return new CommentImpl(new CommentModel(data));
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
    public Element getElementById(String elementId) {
        val jsoupEl = document.getElementById(elementId.toString());
        return (Element) ScriptDOMFactory.get(jsoupEl);
    }

    @Override
    public HTMLCollection children() {
        return new HTMLCollectionImpl(document.children());
    }

    @Override
    public Element firstElementChild() {
        val firstModel = document.children().first();
        if (firstModel != null) {
            return ScriptDOMFactory.getElement(firstModel);
        } else {
            return null;
        }
    }

    @Override
    public Element lastElementChild() {
        val lastModel = document.children().last();
        if (lastModel != null) {
            return ScriptDOMFactory.getElement(lastModel);
        } else {
            return null;
        }
    }

    @Override
    public Integer childElementCount() {
        return document.children().size();
    }

    @Override
    public void prepend(Object... nodes) {
        
    }

    @Override
    public void append(Object... nodes) {

    }

    @Override
    public Element querySelector(String selectors) {
        val selected = document.select(selectors);
        if (selected.size() > 0) {
            Element bound = (Element) ScriptDOMFactory.get(selected.first());
            if (bound == null) {
                bound = new ElementImpl(selected.first());
                ScriptDOMFactory.put(selected.first(), bound);
            }

            return bound;
        }
        return null;
    }

    @Override
    public NodeList querySelectorAll(String selectors) {
        val selected = document.select(selectors);
        val nodes = new ArrayList<NodeModel>(selected);
        return new NodeListImpl(nodes);
    }
}
