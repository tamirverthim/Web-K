package com.earnix.webk.runtime.whatwg_dom.impl.select;

import com.earnix.webk.runtime.whatwg_dom.impl.Jsoup;
import com.earnix.webk.runtime.impl.NodeImpl;
import com.earnix.webk.runtime.whatwg_dom.impl.DocumentImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TraversorTest {
    // Note: NodeTraversor.traverse(new NodeVisitor) is tested in
    // ElementsTest#traverse()

    @Test
    public void filterVisit() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p></div><div>There</div>");
        final StringBuilder accum = new StringBuilder();
        NodeTraversor.filter(new NodeFilter() {
            public FilterResult head(NodeImpl node, int depth) {
                accum.append("<" + node.nodeName() + ">");
                return FilterResult.CONTINUE;
            }

            public FilterResult tail(NodeImpl node, int depth) {
                accum.append("</" + node.nodeName() + ">");
                return FilterResult.CONTINUE;
            }
        }, doc.select("div"));
        assertEquals("<div><p><#text></#text></p></div><div><#text></#text></div>", accum.toString());
    }

    @Test
    public void filterSkipChildren() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p></div><div>There</div>");
        final StringBuilder accum = new StringBuilder();
        NodeTraversor.filter(new NodeFilter() {
            public FilterResult head(NodeImpl node, int depth) {
                accum.append("<" + node.nodeName() + ">");
                // OMIT contents of p:
                return ("p".equals(node.nodeName())) ? FilterResult.SKIP_CHILDREN : FilterResult.CONTINUE;
            }

            public FilterResult tail(NodeImpl node, int depth) {
                accum.append("</" + node.nodeName() + ">");
                return FilterResult.CONTINUE;
            }
        }, doc.select("div"));
        assertEquals("<div><p></p></div><div><#text></#text></div>", accum.toString());
    }

    @Test
    public void filterSkipEntirely() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p></div><div>There</div>");
        final StringBuilder accum = new StringBuilder();
        NodeTraversor.filter(new NodeFilter() {
            public FilterResult head(NodeImpl node, int depth) {
                // OMIT p:
                if ("p".equals(node.nodeName()))
                    return FilterResult.SKIP_ENTIRELY;
                accum.append("<" + node.nodeName() + ">");
                return FilterResult.CONTINUE;
            }

            public FilterResult tail(NodeImpl node, int depth) {
                accum.append("</" + node.nodeName() + ">");
                return FilterResult.CONTINUE;
            }
        }, doc.select("div"));
        assertEquals("<div></div><div><#text></#text></div>", accum.toString());
    }

    @Test
    public void filterRemove() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p></div><div>There be <b>bold</b></div>");
        NodeTraversor.filter(new NodeFilter() {
            public FilterResult head(NodeImpl node, int depth) {
                // Delete "p" in head:
                return ("p".equals(node.nodeName())) ? FilterResult.REMOVE : FilterResult.CONTINUE;
            }

            public FilterResult tail(NodeImpl node, int depth) {
                // Delete "b" in tail:
                return ("b".equals(node.nodeName())) ? FilterResult.REMOVE : FilterResult.CONTINUE;
            }
        }, doc.select("div"));
        assertEquals("<div></div>\n<div>\n There be \n</div>", doc.select("body").html());
    }

    @Test
    public void filterStop() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p></div><div>There</div>");
        final StringBuilder accum = new StringBuilder();
        NodeTraversor.filter(new NodeFilter() {
            public FilterResult head(NodeImpl node, int depth) {
                accum.append("<" + node.nodeName() + ">");
                return FilterResult.CONTINUE;
            }

            public FilterResult tail(NodeImpl node, int depth) {
                accum.append("</" + node.nodeName() + ">");
                // Stop after p.
                return ("p".equals(node.nodeName())) ? FilterResult.STOP : FilterResult.CONTINUE;
            }
        }, doc.select("div"));
        assertEquals("<div><p><#text></#text></p>", accum.toString());
    }
}
