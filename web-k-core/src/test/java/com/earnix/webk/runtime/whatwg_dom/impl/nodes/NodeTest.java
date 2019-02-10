package com.earnix.webk.runtime.whatwg_dom.impl.nodes;

import com.earnix.webk.runtime.whatwg_dom.impl.Jsoup;
import com.earnix.webk.runtime.whatwg_dom.impl.TextUtil;
import com.earnix.webk.runtime.whatwg_dom.impl.parser.Tag;
import com.earnix.webk.runtime.whatwg_dom.impl.select.NodeVisitor;
import com.earnix.webk.runtime.impl.ElementImpl;
import com.earnix.webk.runtime.impl.NodeImpl;
import com.earnix.webk.runtime.whatwg_dom.impl.DocumentImpl;
import com.earnix.webk.runtime.whatwg_dom.impl.TextImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests Nodes
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class NodeTest {
    @Test
    public void handlesBaseUri() {
        Tag tag = Tag.valueOf("a");
        AttributesModel attribs = new AttributesModel();
        attribs.put("relHref", "/foo");
        attribs.put("absHref", "http://bar/qux");

        ElementImpl noBase = new ElementImpl(tag, "", attribs);
        assertEquals("", noBase.absUrl("relHref")); // with no base, should NOT fallback to href attrib, whatever it is
        assertEquals("http://bar/qux", noBase.absUrl("absHref")); // no base but valid attrib, return attrib

        ElementImpl withBase = new ElementImpl(tag, "http://foo/", attribs);
        assertEquals("http://foo/foo", withBase.absUrl("relHref")); // construct abs from base + rel
        assertEquals("http://bar/qux", withBase.absUrl("absHref")); // href is abs, so returns that
        assertEquals("", withBase.absUrl("noval"));

        ElementImpl dodgyBase = new ElementImpl(tag, "wtf://no-such-protocol/", attribs);
        assertEquals("http://bar/qux", dodgyBase.absUrl("absHref")); // base fails, but href good, so get that
        assertEquals("", dodgyBase.absUrl("relHref")); // base fails, only rel href, so return nothing 
    }

    @Test
    public void setBaseUriIsRecursive() {
        DocumentImpl doc = Jsoup.parse("<div><p></p></div>");
        String baseUri = "https://jsoup.org";
        doc.setBaseUri(baseUri);

        assertEquals(baseUri, doc.baseUri());
        assertEquals(baseUri, doc.select("div").first().baseUri());
        assertEquals(baseUri, doc.select("p").first().baseUri());
    }

    @Test
    public void handlesAbsPrefix() {
        DocumentImpl doc = Jsoup.parse("<a href=/foo>Hello</a>", "https://jsoup.org/");
        ElementImpl a = doc.select("a").first();
        assertEquals("/foo", a.attr("href"));
        assertEquals("https://jsoup.org/foo", a.attr("abs:href"));
        assertTrue(a.hasAttr("abs:href"));
    }

    @Test
    public void handlesAbsOnImage() {
        DocumentImpl doc = Jsoup.parse("<p><img src=\"/rez/osi_logo.png\" /></p>", "https://jsoup.org/");
        ElementImpl img = doc.select("img").first();
        assertEquals("https://jsoup.org/rez/osi_logo.png", img.attr("abs:src"));
        assertEquals(img.absUrl("src"), img.attr("abs:src"));
    }

    @Test
    public void handlesAbsPrefixOnHasAttr() {
        // 1: no abs url; 2: has abs url
        DocumentImpl doc = Jsoup.parse("<a id=1 href='/foo'>One</a> <a id=2 href='https://jsoup.org/'>Two</a>");
        ElementImpl one = doc.select("#1").first();
        ElementImpl two = doc.select("#2").first();

        assertFalse(one.hasAttr("abs:href"));
        assertTrue(one.hasAttr("href"));
        assertEquals("", one.absUrl("href"));

        assertTrue(two.hasAttr("abs:href"));
        assertTrue(two.hasAttr("href"));
        assertEquals("https://jsoup.org/", two.absUrl("href"));
    }

    @Test
    public void literalAbsPrefix() {
        // if there is a literal attribute "abs:xxx", don't try and make absolute.
        DocumentImpl doc = Jsoup.parse("<a abs:href='odd'>One</a>");
        ElementImpl el = doc.select("a").first();
        assertTrue(el.hasAttr("abs:href"));
        assertEquals("odd", el.attr("abs:href"));
    }

    @Test
    public void handleAbsOnFileUris() {
        DocumentImpl doc = Jsoup.parse("<a href='password'>One/a><a href='/var/log/messages'>Two</a>", "file:/etc/");
        ElementImpl one = doc.select("a").first();
        assertEquals("file:/etc/password", one.absUrl("href"));
        ElementImpl two = doc.select("a").get(1);
        assertEquals("file:/var/log/messages", two.absUrl("href"));
    }

    @Test
    public void handleAbsOnLocalhostFileUris() {
        DocumentImpl doc = Jsoup.parse("<a href='password'>One/a><a href='/var/log/messages'>Two</a>", "file://localhost/etc/");
        ElementImpl one = doc.select("a").first();
        assertEquals("file://localhost/etc/password", one.absUrl("href"));
    }

    @Test
    public void handlesAbsOnProtocolessAbsoluteUris() {
        DocumentImpl doc1 = Jsoup.parse("<a href='//example.net/foo'>One</a>", "http://example.com/");
        DocumentImpl doc2 = Jsoup.parse("<a href='//example.net/foo'>One</a>", "https://example.com/");

        ElementImpl one = doc1.select("a").first();
        ElementImpl two = doc2.select("a").first();

        assertEquals("http://example.net/foo", one.absUrl("href"));
        assertEquals("https://example.net/foo", two.absUrl("href"));

        DocumentImpl doc3 = Jsoup.parse("<img src=//www.google.com/images/errors/logo_sm.gif alt=Google>", "https://google.com");
        assertEquals("https://www.google.com/images/errors/logo_sm.gif", doc3.select("img").attr("abs:src"));
    }

    /*
    Test for an issue with Java's abs URL handler.
     */
    @Test
    public void absHandlesRelativeQuery() {
        DocumentImpl doc = Jsoup.parse("<a href='?foo'>One</a> <a href='bar.html?foo'>Two</a>", "https://jsoup.org/path/file?bar");

        ElementImpl a1 = doc.select("a").first();
        assertEquals("https://jsoup.org/path/file?foo", a1.absUrl("href"));

        ElementImpl a2 = doc.select("a").get(1);
        assertEquals("https://jsoup.org/path/bar.html?foo", a2.absUrl("href"));
    }

    @Test
    public void absHandlesDotFromIndex() {
        DocumentImpl doc = Jsoup.parse("<a href='./one/two.html'>One</a>", "http://example.com");
        ElementImpl a1 = doc.select("a").first();
        assertEquals("http://example.com/one/two.html", a1.absUrl("href"));
    }

    @Test
    public void testRemove() {
        DocumentImpl doc = Jsoup.parse("<p>One <span>two</span> three</p>");
        ElementImpl p = doc.select("p").first();
        p.childNode(0).remove();

        assertEquals("two three", p.text());
        Assert.assertEquals("<span>two</span> three", TextUtil.stripNewlines(p.html()));
    }

    @Test
    public void testReplace() {
        DocumentImpl doc = Jsoup.parse("<p>One <span>two</span> three</p>");
        ElementImpl p = doc.select("p").first();
        ElementImpl insert = doc.createElement("em").text("foo");
        p.childNode(1).replaceWith(insert);

        assertEquals("One <em>foo</em> three", p.html());
    }

    @Test
    public void ownerDocument() {
        DocumentImpl doc = Jsoup.parse("<p>Hello");
        ElementImpl p = doc.select("p").first();
        assertTrue(p.ownerDocument() == doc);
        assertTrue(doc.ownerDocument() == doc);
        assertNull(doc.parent());
    }

    @Test
    public void root() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello");
        ElementImpl p = doc.select("p").first();
        NodeImpl
                root = p.root();
        assertTrue(doc == root);
        assertNull(root.parent());
        assertTrue(doc.root() == doc);
        assertTrue(doc.root() == doc.ownerDocument());

        ElementImpl standAlone = new ElementImpl(Tag.valueOf("p"), "");
        assertTrue(standAlone.parent() == null);
        assertTrue(standAlone.root() == standAlone);
        assertTrue(standAlone.ownerDocument() == null);
    }

    @Test
    public void before() {
        DocumentImpl doc = Jsoup.parse("<p>One <b>two</b> three</p>");
        ElementImpl newNode = new ElementImpl(Tag.valueOf("em"), "");
        newNode.appendText("four");

        doc.select("b").first().before(newNode);
        assertEquals("<p>One <em>four</em><b>two</b> three</p>", doc.getBody().html());

        doc.select("b").first().before("<i>five</i>");
        assertEquals("<p>One <em>four</em><i>five</i><b>two</b> three</p>", doc.getBody().html());
    }

    @Test
    public void after() {
        DocumentImpl doc = Jsoup.parse("<p>One <b>two</b> three</p>");
        ElementImpl newNode = new ElementImpl(Tag.valueOf("em"), "");
        newNode.appendText("four");

        doc.select("b").first().after(newNode);
        assertEquals("<p>One <b>two</b><em>four</em> three</p>", doc.getBody().html());

        doc.select("b").first().after("<i>five</i>");
        assertEquals("<p>One <b>two</b><i>five</i><em>four</em> three</p>", doc.getBody().html());
    }

    @Test
    public void unwrap() {
        DocumentImpl doc = Jsoup.parse("<div>One <span>Two <b>Three</b></span> Four</div>");
        ElementImpl span = doc.select("span").first();
        NodeImpl twoText = span.childNode(0);
        NodeImpl node = span.unwrap();

        assertEquals("<div>One Two <b>Three</b> Four</div>", TextUtil.stripNewlines(doc.getBody().html()));
        assertTrue(node instanceof TextImpl);
        assertEquals("Two ", ((TextImpl) node).text());
        assertEquals(node, twoText);
        assertEquals(node.parent(), doc.select("div").first());
    }

    @Test
    public void unwrapNoChildren() {
        DocumentImpl doc = Jsoup.parse("<div>One <span></span> Two</div>");
        ElementImpl span = doc.select("span").first();
        NodeImpl node = span.unwrap();
        assertEquals("<div>One  Two</div>", TextUtil.stripNewlines(doc.getBody().html()));
        assertTrue(node == null);
    }

    @Test
    public void traverse() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p></div><div>There</div>");
        final StringBuilder accum = new StringBuilder();
        doc.select("div").first().traverse(new NodeVisitor() {
            public void head(NodeImpl node, int depth) {
                accum.append("<" + node.nodeName() + ">");
            }

            public void tail(NodeImpl node, int depth) {
                accum.append("</" + node.nodeName() + ">");
            }
        });
        assertEquals("<div><p><#text></#text></p></div>", accum.toString());
    }

    @Test
    public void orphanNodeReturnsNullForSiblingElements() {
        NodeImpl node = new ElementImpl(Tag.valueOf("p"), "");
        ElementImpl el = new ElementImpl(Tag.valueOf("p"), "");

        assertEquals(0, node.siblingIndex());
        assertEquals(0, node.siblingNodes().size());

        assertNull(node.previousSibling());
        assertNull(node.nextSibling());

        assertEquals(0, el.siblingElements().size());
        assertNull(el.previousElementSibling());
        assertNull(el.nextElementSibling());
    }

    @Test
    public void nodeIsNotASiblingOfItself() {
        DocumentImpl doc = Jsoup.parse("<div><p>One<p>Two<p>Three</div>");
        ElementImpl p2 = doc.select("p").get(1);

        assertEquals("Two", p2.text());
        List<NodeImpl> nodes = p2.siblingNodes();
        assertEquals(2, nodes.size());
        assertEquals("<p>One</p>", nodes.get(0).outerHtml());
        assertEquals("<p>Three</p>", nodes.get(1).outerHtml());
    }

    @Test
    public void childNodesCopy() {
        DocumentImpl doc = Jsoup.parse("<div id=1>Text 1 <p>One</p> Text 2 <p>Two<p>Three</div><div id=2>");
        ElementImpl div1 = doc.select("#1").first();
        ElementImpl div2 = doc.select("#2").first();
        List<NodeImpl> divChildren = div1.childNodesCopy();
        assertEquals(5, divChildren.size());
        TextImpl tn1 = (TextImpl) div1.childNode(0);
        TextImpl tn2 = (TextImpl) divChildren.get(0);
        tn2.text("Text 1 updated");
        assertEquals("Text 1 ", tn1.text());
        div2.insertChildren(-1, divChildren);
        assertEquals("<div id=\"1\">Text 1 <p>One</p> Text 2 <p>Two</p><p>Three</p></div><div id=\"2\">Text 1 updated"
                + "<p>One</p> Text 2 <p>Two</p><p>Three</p></div>", TextUtil.stripNewlines(doc.getBody().html()));
    }

    @Test
    public void supportsClone() {
        DocumentImpl doc = Jsoup.parse("<div class=foo>Text</div>");
        ElementImpl el = doc.select("div").first();
        assertTrue(el.hasClass("foo"));

        ElementImpl elClone = doc.clone().select("div").first();
        assertTrue(elClone.hasClass("foo"));
        assertTrue(elClone.text().equals("Text"));

        el.removeClass("foo");
        el.text("None");
        assertFalse(el.hasClass("foo"));
        assertTrue(elClone.hasClass("foo"));
        assertTrue(el.text().equals("None"));
        assertTrue(elClone.text().equals("Text"));
    }

    @Test
    public void changingAttributeValueShouldReplaceExistingAttributeCaseInsensitive() {
        DocumentImpl document = Jsoup.parse("<INPUT id=\"foo\" NAME=\"foo\" VALUE=\"\">");
        ElementImpl inputElement = document.select("#foo").first();

        inputElement.attr("value", "bar");

        assertEquals(singletonAttributes("value", "bar"), getAttributesCaseInsensitive(inputElement, "value"));
    }

    private AttributesModel getAttributesCaseInsensitive(ElementImpl element, String attributeName) {
        AttributesModel matches = new AttributesModel();
        for (AttributeModel attribute : element.getAttributes()) {
            if (attribute.getKey().equalsIgnoreCase(attributeName)) {
                matches.put(attribute);
            }
        }
        return matches;
    }

    private AttributesModel singletonAttributes(String key, String value) {
        AttributesModel attributes = new AttributesModel();
        attributes.put(key, value);
        return attributes;
    }
}
