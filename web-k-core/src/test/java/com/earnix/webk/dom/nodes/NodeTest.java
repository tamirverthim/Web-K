package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.TextUtil;
import com.earnix.webk.dom.parser.Tag;
import com.earnix.webk.dom.select.NodeVisitor;
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

        ElementModel noBase = new ElementModel(tag, "", attribs);
        assertEquals("", noBase.absUrl("relHref")); // with no base, should NOT fallback to href attrib, whatever it is
        assertEquals("http://bar/qux", noBase.absUrl("absHref")); // no base but valid attrib, return attrib

        ElementModel withBase = new ElementModel(tag, "http://foo/", attribs);
        assertEquals("http://foo/foo", withBase.absUrl("relHref")); // construct abs from base + rel
        assertEquals("http://bar/qux", withBase.absUrl("absHref")); // href is abs, so returns that
        assertEquals("", withBase.absUrl("noval"));

        ElementModel dodgyBase = new ElementModel(tag, "wtf://no-such-protocol/", attribs);
        assertEquals("http://bar/qux", dodgyBase.absUrl("absHref")); // base fails, but href good, so get that
        assertEquals("", dodgyBase.absUrl("relHref")); // base fails, only rel href, so return nothing 
    }

    @Test
    public void setBaseUriIsRecursive() {
        DocumentModel doc = Jsoup.parse("<div><p></p></div>");
        String baseUri = "https://jsoup.org";
        doc.setBaseUri(baseUri);

        assertEquals(baseUri, doc.baseUri());
        assertEquals(baseUri, doc.select("div").first().baseUri());
        assertEquals(baseUri, doc.select("p").first().baseUri());
    }

    @Test
    public void handlesAbsPrefix() {
        DocumentModel doc = Jsoup.parse("<a href=/foo>Hello</a>", "https://jsoup.org/");
        ElementModel a = doc.select("a").first();
        assertEquals("/foo", a.attr("href"));
        assertEquals("https://jsoup.org/foo", a.attr("abs:href"));
        assertTrue(a.hasAttr("abs:href"));
    }

    @Test
    public void handlesAbsOnImage() {
        DocumentModel doc = Jsoup.parse("<p><img src=\"/rez/osi_logo.png\" /></p>", "https://jsoup.org/");
        ElementModel img = doc.select("img").first();
        assertEquals("https://jsoup.org/rez/osi_logo.png", img.attr("abs:src"));
        assertEquals(img.absUrl("src"), img.attr("abs:src"));
    }

    @Test
    public void handlesAbsPrefixOnHasAttr() {
        // 1: no abs url; 2: has abs url
        DocumentModel doc = Jsoup.parse("<a id=1 href='/foo'>One</a> <a id=2 href='https://jsoup.org/'>Two</a>");
        ElementModel one = doc.select("#1").first();
        ElementModel two = doc.select("#2").first();

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
        DocumentModel doc = Jsoup.parse("<a abs:href='odd'>One</a>");
        ElementModel el = doc.select("a").first();
        assertTrue(el.hasAttr("abs:href"));
        assertEquals("odd", el.attr("abs:href"));
    }

    @Test
    public void handleAbsOnFileUris() {
        DocumentModel doc = Jsoup.parse("<a href='password'>One/a><a href='/var/log/messages'>Two</a>", "file:/etc/");
        ElementModel one = doc.select("a").first();
        assertEquals("file:/etc/password", one.absUrl("href"));
        ElementModel two = doc.select("a").get(1);
        assertEquals("file:/var/log/messages", two.absUrl("href"));
    }

    @Test
    public void handleAbsOnLocalhostFileUris() {
        DocumentModel doc = Jsoup.parse("<a href='password'>One/a><a href='/var/log/messages'>Two</a>", "file://localhost/etc/");
        ElementModel one = doc.select("a").first();
        assertEquals("file://localhost/etc/password", one.absUrl("href"));
    }

    @Test
    public void handlesAbsOnProtocolessAbsoluteUris() {
        DocumentModel doc1 = Jsoup.parse("<a href='//example.net/foo'>One</a>", "http://example.com/");
        DocumentModel doc2 = Jsoup.parse("<a href='//example.net/foo'>One</a>", "https://example.com/");

        ElementModel one = doc1.select("a").first();
        ElementModel two = doc2.select("a").first();

        assertEquals("http://example.net/foo", one.absUrl("href"));
        assertEquals("https://example.net/foo", two.absUrl("href"));

        DocumentModel doc3 = Jsoup.parse("<img src=//www.google.com/images/errors/logo_sm.gif alt=Google>", "https://google.com");
        assertEquals("https://www.google.com/images/errors/logo_sm.gif", doc3.select("img").attr("abs:src"));
    }

    /*
    Test for an issue with Java's abs URL handler.
     */
    @Test
    public void absHandlesRelativeQuery() {
        DocumentModel doc = Jsoup.parse("<a href='?foo'>One</a> <a href='bar.html?foo'>Two</a>", "https://jsoup.org/path/file?bar");

        ElementModel a1 = doc.select("a").first();
        assertEquals("https://jsoup.org/path/file?foo", a1.absUrl("href"));

        ElementModel a2 = doc.select("a").get(1);
        assertEquals("https://jsoup.org/path/bar.html?foo", a2.absUrl("href"));
    }

    @Test
    public void absHandlesDotFromIndex() {
        DocumentModel doc = Jsoup.parse("<a href='./one/two.html'>One</a>", "http://example.com");
        ElementModel a1 = doc.select("a").first();
        assertEquals("http://example.com/one/two.html", a1.absUrl("href"));
    }

    @Test
    public void testRemove() {
        DocumentModel doc = Jsoup.parse("<p>One <span>two</span> three</p>");
        ElementModel p = doc.select("p").first();
        p.childNode(0).remove();

        assertEquals("two three", p.text());
        Assert.assertEquals("<span>two</span> three", TextUtil.stripNewlines(p.html()));
    }

    @Test
    public void testReplace() {
        DocumentModel doc = Jsoup.parse("<p>One <span>two</span> three</p>");
        ElementModel p = doc.select("p").first();
        ElementModel insert = doc.createElement("em").text("foo");
        p.childNode(1).replaceWith(insert);

        assertEquals("One <em>foo</em> three", p.html());
    }

    @Test
    public void ownerDocument() {
        DocumentModel doc = Jsoup.parse("<p>Hello");
        ElementModel p = doc.select("p").first();
        assertTrue(p.ownerDocument() == doc);
        assertTrue(doc.ownerDocument() == doc);
        assertNull(doc.parent());
    }

    @Test
    public void root() {
        DocumentModel doc = Jsoup.parse("<div><p>Hello");
        ElementModel p = doc.select("p").first();
        NodeModel root = p.root();
        assertTrue(doc == root);
        assertNull(root.parent());
        assertTrue(doc.root() == doc);
        assertTrue(doc.root() == doc.ownerDocument());

        ElementModel standAlone = new ElementModel(Tag.valueOf("p"), "");
        assertTrue(standAlone.parent() == null);
        assertTrue(standAlone.root() == standAlone);
        assertTrue(standAlone.ownerDocument() == null);
    }

    @Test
    public void before() {
        DocumentModel doc = Jsoup.parse("<p>One <b>two</b> three</p>");
        ElementModel newNode = new ElementModel(Tag.valueOf("em"), "");
        newNode.appendText("four");

        doc.select("b").first().before(newNode);
        assertEquals("<p>One <em>four</em><b>two</b> three</p>", doc.body().html());

        doc.select("b").first().before("<i>five</i>");
        assertEquals("<p>One <em>four</em><i>five</i><b>two</b> three</p>", doc.body().html());
    }

    @Test
    public void after() {
        DocumentModel doc = Jsoup.parse("<p>One <b>two</b> three</p>");
        ElementModel newNode = new ElementModel(Tag.valueOf("em"), "");
        newNode.appendText("four");

        doc.select("b").first().after(newNode);
        assertEquals("<p>One <b>two</b><em>four</em> three</p>", doc.body().html());

        doc.select("b").first().after("<i>five</i>");
        assertEquals("<p>One <b>two</b><i>five</i><em>four</em> three</p>", doc.body().html());
    }

    @Test
    public void unwrap() {
        DocumentModel doc = Jsoup.parse("<div>One <span>Two <b>Three</b></span> Four</div>");
        ElementModel span = doc.select("span").first();
        NodeModel twoText = span.childNode(0);
        NodeModel node = span.unwrap();

        assertEquals("<div>One Two <b>Three</b> Four</div>", TextUtil.stripNewlines(doc.body().html()));
        assertTrue(node instanceof TextNodeModel);
        assertEquals("Two ", ((TextNodeModel) node).text());
        assertEquals(node, twoText);
        assertEquals(node.parent(), doc.select("div").first());
    }

    @Test
    public void unwrapNoChildren() {
        DocumentModel doc = Jsoup.parse("<div>One <span></span> Two</div>");
        ElementModel span = doc.select("span").first();
        NodeModel node = span.unwrap();
        assertEquals("<div>One  Two</div>", TextUtil.stripNewlines(doc.body().html()));
        assertTrue(node == null);
    }

    @Test
    public void traverse() {
        DocumentModel doc = Jsoup.parse("<div><p>Hello</p></div><div>There</div>");
        final StringBuilder accum = new StringBuilder();
        doc.select("div").first().traverse(new NodeVisitor() {
            public void head(NodeModel node, int depth) {
                accum.append("<" + node.nodeName() + ">");
            }

            public void tail(NodeModel node, int depth) {
                accum.append("</" + node.nodeName() + ">");
            }
        });
        assertEquals("<div><p><#text></#text></p></div>", accum.toString());
    }

    @Test
    public void orphanNodeReturnsNullForSiblingElements() {
        NodeModel node = new ElementModel(Tag.valueOf("p"), "");
        ElementModel el = new ElementModel(Tag.valueOf("p"), "");

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
        DocumentModel doc = Jsoup.parse("<div><p>One<p>Two<p>Three</div>");
        ElementModel p2 = doc.select("p").get(1);

        assertEquals("Two", p2.text());
        List<NodeModel> nodes = p2.siblingNodes();
        assertEquals(2, nodes.size());
        assertEquals("<p>One</p>", nodes.get(0).outerHtml());
        assertEquals("<p>Three</p>", nodes.get(1).outerHtml());
    }

    @Test
    public void childNodesCopy() {
        DocumentModel doc = Jsoup.parse("<div id=1>Text 1 <p>One</p> Text 2 <p>Two<p>Three</div><div id=2>");
        ElementModel div1 = doc.select("#1").first();
        ElementModel div2 = doc.select("#2").first();
        List<NodeModel> divChildren = div1.childNodesCopy();
        assertEquals(5, divChildren.size());
        TextNodeModel tn1 = (TextNodeModel) div1.childNode(0);
        TextNodeModel tn2 = (TextNodeModel) divChildren.get(0);
        tn2.text("Text 1 updated");
        assertEquals("Text 1 ", tn1.text());
        div2.insertChildren(-1, divChildren);
        assertEquals("<div id=\"1\">Text 1 <p>One</p> Text 2 <p>Two</p><p>Three</p></div><div id=\"2\">Text 1 updated"
                + "<p>One</p> Text 2 <p>Two</p><p>Three</p></div>", TextUtil.stripNewlines(doc.body().html()));
    }

    @Test
    public void supportsClone() {
        DocumentModel doc = com.earnix.webk.dom.Jsoup.parse("<div class=foo>Text</div>");
        ElementModel el = doc.select("div").first();
        assertTrue(el.hasClass("foo"));

        ElementModel elClone = doc.clone().select("div").first();
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
        DocumentModel document = Jsoup.parse("<INPUT id=\"foo\" NAME=\"foo\" VALUE=\"\">");
        ElementModel inputElement = document.select("#foo").first();

        inputElement.attr("value", "bar");

        assertEquals(singletonAttributes("value", "bar"), getAttributesCaseInsensitive(inputElement, "value"));
    }

    private AttributesModel getAttributesCaseInsensitive(ElementModel element, String attributeName) {
        AttributesModel matches = new AttributesModel();
        for (AttributeModel attribute : element.attributes()) {
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
