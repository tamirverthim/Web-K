package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.TextUtil;
import com.earnix.webk.dom.parser.Tag;
import com.earnix.webk.dom.select.Elements;
import com.earnix.webk.script.html.impl.DocumentImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.script.whatwg_dom.impl.TextImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for Element (DOM stuff mostly).
 *
 * @author Jonathan Hedley
 */
public class ElementTest {
    private String reference = "<div id=div1><p>Hello</p><p>Another <b>element</b></p><div id=div2><img src=foo.png></div></div>";

    @Test
    public void getElementsByTagName() {
        DocumentImpl doc = Jsoup.parse(reference);
        List<ElementImpl> divs = doc.getElementsByTag("div");
        assertEquals(2, divs.size());
        assertEquals("div1", divs.get(0).id().get());
        assertEquals("div2", divs.get(1).id().get());

        List<ElementImpl> ps = doc.getElementsByTag("p");
        assertEquals(2, ps.size());
        assertEquals("Hello", ((TextImpl) ps.get(0).childNode(0)).getWholeText());
        assertEquals("Another ", ((TextImpl) ps.get(1).childNode(0)).getWholeText());
        List<ElementImpl> ps2 = doc.getElementsByTag("P");
        assertEquals(ps, ps2);

        List<ElementImpl> imgs = doc.getElementsByTag("img");
        assertEquals("foo.png", imgs.get(0).attr("src"));

        List<ElementImpl> empty = doc.getElementsByTag("wtf");
        assertEquals(0, empty.size());
    }

    @Test
    public void getNamespacedElementsByTag() {
        DocumentImpl doc = Jsoup.parse("<div><abc:def id=1>Hello</abc:def></div>");
        Elements els = doc.getElementsByTag("abc:def");
        assertEquals(1, els.size());
        assertEquals("1", els.first().id().get());
        assertEquals("abc:def", els.first().tagName());
    }

    @Test
    public void testGetElementById() {
        DocumentImpl doc = Jsoup.parse(reference);
        ElementImpl div = doc.getElementById("div1");
        assertEquals("div1", div.id().get());
        assertNull(doc.getElementById("none"));

        DocumentImpl doc2 = Jsoup.parse("<div id=1><div id=2><p>Hello <span id=2>world!</span></p></div></div>");
        ElementImpl div2 = doc2.getElementById("2");
        assertEquals("div", div2.tagName()); // not the span
        ElementImpl span = div2.child(0).getElementById("2"); // called from <p> context should be span
        assertEquals("span", span.tagName());
    }

    @Test
    public void testGetText() {
        DocumentImpl doc = Jsoup.parse(reference);
        assertEquals("Hello Another element", doc.text());
        assertEquals("Another element", doc.getElementsByTag("p").get(1).text());
    }

    @Test
    public void testGetChildText() {
        DocumentImpl doc = Jsoup.parse("<p>Hello <b>there</b> now");
        ElementImpl p = doc.select("p").first();
        assertEquals("Hello there now", p.text());
        assertEquals("Hello now", p.ownText());
    }

    @Test
    public void testNormalisesText() {
        String h = "<p>Hello<p>There.</p> \n <p>Here <b>is</b> \n s<b>om</b>e text.";
        DocumentImpl doc = Jsoup.parse(h);
        String text = doc.text();
        assertEquals("Hello There. Here is some text.", text);
    }

    @Test
    public void testKeepsPreText() {
        String h = "<p>Hello \n \n there.</p> <div><pre>  What's \n\n  that?</pre>";
        DocumentImpl doc = Jsoup.parse(h);
        assertEquals("Hello there.   What's \n\n  that?", doc.text());
    }

    @Test
    public void testKeepsPreTextInCode() {
        String h = "<pre><code>code\n\ncode</code></pre>";
        DocumentImpl doc = Jsoup.parse(h);
        assertEquals("code\n\ncode", doc.text());
        assertEquals("<pre><code>code\n\ncode</code></pre>", doc.getBody().html());
    }

    @Test
    public void testKeepsPreTextAtDepth() {
        String h = "<pre><code><span><b>code\n\ncode</b></span></code></pre>";
        DocumentImpl doc = Jsoup.parse(h);
        assertEquals("code\n\ncode", doc.text());
        assertEquals("<pre><code><span><b>code\n\ncode</b></span></code></pre>", doc.getBody().html());
    }

    @Test
    public void testBrHasSpace() {
        DocumentImpl doc = Jsoup.parse("<p>Hello<br>there</p>");
        assertEquals("Hello there", doc.text());
        assertEquals("Hello there", doc.select("p").first().ownText());

        doc = Jsoup.parse("<p>Hello <br> there</p>");
        assertEquals("Hello there", doc.text());
    }

    @Test
    public void testWholeText() {
        DocumentImpl doc = Jsoup.parse("<p> Hello\nthere &nbsp;  </p>");
        assertEquals(" Hello\nthere    ", doc.wholeText());

        doc = Jsoup.parse("<p>Hello  \n  there</p>");
        assertEquals("Hello  \n  there", doc.wholeText());

        doc = Jsoup.parse("<p>Hello  <div>\n  there</div></p>");
        assertEquals("Hello  \n  there", doc.wholeText());
    }

    @Test
    public void testGetSiblings() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello<p id=1>there<p>this<p>is<p>an<p id=last>element</div>");
        ElementImpl p = doc.getElementById("1");
        assertEquals("there", p.text());
        assertEquals("Hello", p.previousElementSibling().text());
        assertEquals("this", p.nextElementSibling().text());
        assertEquals("Hello", p.firstElementSibling().text());
        assertEquals("element", p.lastElementSibling().text());
    }

    @Test
    public void testGetSiblingsWithDuplicateContent() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello<p id=1>there<p>this<p>this<p>is<p>an<p id=last>element</div>");
        ElementImpl p = doc.getElementById("1");
        assertEquals("there", p.text());
        assertEquals("Hello", p.previousElementSibling().text());
        assertEquals("this", p.nextElementSibling().text());
        assertEquals("this", p.nextElementSibling().nextElementSibling().text());
        assertEquals("is", p.nextElementSibling().nextElementSibling().nextElementSibling().text());
        assertEquals("Hello", p.firstElementSibling().text());
        assertEquals("element", p.lastElementSibling().text());
    }

    @Test
    public void testGetParents() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello <span>there</span></div>");
        ElementImpl span = doc.select("span").first();
        Elements parents = span.parents();

        assertEquals(4, parents.size());
        assertEquals("p", parents.get(0).tagName());
        assertEquals("div", parents.get(1).tagName());
        assertEquals("body", parents.get(2).tagName());
        assertEquals("html", parents.get(3).tagName());
    }

    @Test
    public void testElementSiblingIndex() {
        DocumentImpl doc = Jsoup.parse("<div><p>One</p>...<p>Two</p>...<p>Three</p>");
        Elements ps = doc.select("p");
        assertTrue(0 == ps.get(0).elementSiblingIndex());
        assertTrue(1 == ps.get(1).elementSiblingIndex());
        assertTrue(2 == ps.get(2).elementSiblingIndex());
    }

    @Test
    public void testElementSiblingIndexSameContent() {
        DocumentImpl doc = Jsoup.parse("<div><p>One</p>...<p>One</p>...<p>One</p>");
        Elements ps = doc.select("p");
        assertTrue(0 == ps.get(0).elementSiblingIndex());
        assertTrue(1 == ps.get(1).elementSiblingIndex());
        assertTrue(2 == ps.get(2).elementSiblingIndex());
    }

    @Test
    public void testGetElementsWithClass() {
        DocumentImpl doc = Jsoup.parse("<div class='mellow yellow'><span class=mellow>Hello <b class='yellow'>Yellow!</b></span><p>Empty</p></div>");

        List<ElementImpl> els = doc.getElementsByClass("mellow");
        assertEquals(2, els.size());
        assertEquals("div", els.get(0).tagName());
        assertEquals("span", els.get(1).tagName());

        List<ElementImpl> els2 = doc.getElementsByClass("yellow");
        assertEquals(2, els2.size());
        assertEquals("div", els2.get(0).tagName());
        assertEquals("b", els2.get(1).tagName());

        List<ElementImpl> none = doc.getElementsByClass("solo");
        assertEquals(0, none.size());
    }

    @Test
    public void testGetElementsWithAttribute() {
        DocumentImpl doc = Jsoup.parse("<div style='bold'><p title=qux><p><b style></b></p></div>");
        List<ElementImpl> els = doc.getElementsByAttribute("style");
        assertEquals(2, els.size());
        assertEquals("div", els.get(0).tagName());
        assertEquals("b", els.get(1).tagName());

        List<ElementImpl> none = doc.getElementsByAttribute("class");
        assertEquals(0, none.size());
    }

    @Test
    public void testGetElementsWithAttributeDash() {
        DocumentImpl doc = Jsoup.parse("<meta http-equiv=content-type value=utf8 id=1> <meta name=foo content=bar id=2> <div http-equiv=content-type value=utf8 id=3>");
        Elements meta = doc.select("meta[http-equiv=content-type], meta[charset]");
        assertEquals(1, meta.size());
        assertEquals("1", meta.first().id().get());
    }

    @Test
    public void testGetElementsWithAttributeValue() {
        DocumentImpl doc = Jsoup.parse("<div style='bold'><p><p><b style></b></p></div>");
        List<ElementImpl> els = doc.getElementsByAttributeValue("style", "bold");
        assertEquals(1, els.size());
        assertEquals("div", els.get(0).tagName());

        List<ElementImpl> none = doc.getElementsByAttributeValue("style", "none");
        assertEquals(0, none.size());
    }

    @Test
    public void testClassDomMethods() {
        DocumentImpl doc = Jsoup.parse("<div><span class=' mellow yellow '>Hello <b>Yellow</b></span></div>");
        List<ElementImpl> els = doc.getElementsByAttribute("class");
        ElementImpl span = els.get(0);
        assertEquals("mellow yellow", span.className().get());
        assertTrue(span.hasClass("mellow"));
        assertTrue(span.hasClass("yellow"));
        Set<String> classes = span.getClassNames();
        assertEquals(2, classes.size());
        assertTrue(classes.contains("mellow"));
        assertTrue(classes.contains("yellow"));

        assertEquals("", doc.className().get());
        classes = doc.getClassNames();
        assertEquals(0, classes.size());
        assertFalse(doc.hasClass("mellow"));
    }

    @Test
    public void testHasClassDomMethods() {
        Tag tag = Tag.valueOf("a");
        AttributesModel attribs = new AttributesModel();
        ElementImpl el = new ElementImpl(tag, "", attribs);

        attribs.put("class", "toto");
        boolean hasClass = el.hasClass("toto");
        assertTrue(hasClass);

        attribs.put("class", " toto");
        hasClass = el.hasClass("toto");
        assertTrue(hasClass);

        attribs.put("class", "toto ");
        hasClass = el.hasClass("toto");
        assertTrue(hasClass);

        attribs.put("class", "\ttoto ");
        hasClass = el.hasClass("toto");
        assertTrue(hasClass);

        attribs.put("class", "  toto ");
        hasClass = el.hasClass("toto");
        assertTrue(hasClass);

        attribs.put("class", "ab");
        hasClass = el.hasClass("toto");
        assertFalse(hasClass);

        attribs.put("class", "     ");
        hasClass = el.hasClass("toto");
        assertFalse(hasClass);

        attribs.put("class", "tototo");
        hasClass = el.hasClass("toto");
        assertFalse(hasClass);

        attribs.put("class", "raulpismuth  ");
        hasClass = el.hasClass("raulpismuth");
        assertTrue(hasClass);

        attribs.put("class", " abcd  raulpismuth efgh ");
        hasClass = el.hasClass("raulpismuth");
        assertTrue(hasClass);

        attribs.put("class", " abcd efgh raulpismuth");
        hasClass = el.hasClass("raulpismuth");
        assertTrue(hasClass);

        attribs.put("class", " abcd efgh raulpismuth ");
        hasClass = el.hasClass("raulpismuth");
        assertTrue(hasClass);
    }


    @Test
    public void testClassUpdates() {
        DocumentImpl doc = Jsoup.parse("<div class='mellow yellow'></div>");
        ElementImpl div = doc.select("div").first();

        div.addClass("green");
        assertEquals("mellow yellow green", div.className().get());
        div.removeClass("red"); // noop
        div.removeClass("yellow");
        assertEquals("mellow green", div.className().get());
        div.toggleClass("green").toggleClass("red");
        assertEquals("mellow red", div.className().get());
    }

    @Test
    public void testOuterHtml() {
        DocumentImpl doc = Jsoup.parse("<div title='Tags &amp;c.'><img src=foo.png><p><!-- comment -->Hello<p>there");
        Assert.assertEquals("<html><head></head><body><div title=\"Tags &amp;c.\"><img src=\"foo.png\"><p><!-- comment -->Hello</p><p>there</p></div></body></html>",
                TextUtil.stripNewlines(doc.outerHtml()));
    }

    @Test
    public void testInnerHtml() {
        DocumentImpl doc = Jsoup.parse("<div>\n <p>Hello</p> </div>");
        assertEquals("<p>Hello</p>", doc.getElementsByTag("div").get(0).html());
    }

    @Test
    public void testFormatHtml() {
        DocumentImpl doc = Jsoup.parse("<title>Format test</title><div><p>Hello <span>jsoup <span>users</span></span></p><p>Good.</p></div>");
        assertEquals("<html>\n <head>\n  <title>Format test</title>\n </head>\n <body>\n  <div>\n   <p>Hello <span>jsoup <span>users</span></span></p>\n   <p>Good.</p>\n  </div>\n </body>\n</html>", doc.html());
    }

    @Test
    public void testFormatOutline() {
        DocumentImpl doc = Jsoup.parse("<title>Format test</title><div><p>Hello <span>jsoup <span>users</span></span></p><p>Good.</p></div>");
        doc.outputSettings().outline(true);
        assertEquals("<html>\n <head>\n  <title>Format test</title>\n </head>\n <body>\n  <div>\n   <p>\n    Hello \n    <span>\n     jsoup \n     <span>users</span>\n    </span>\n   </p>\n   <p>Good.</p>\n  </div>\n </body>\n</html>", doc.html());
    }

    @Test
    public void testSetIndent() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello\nthere</p></div>");
        doc.outputSettings().indentAmount(0);
        assertEquals("<html>\n<head></head>\n<body>\n<div>\n<p>Hello there</p>\n</div>\n</body>\n</html>", doc.html());
    }

    @Test
    public void testNotPretty() {
        DocumentImpl doc = Jsoup.parse("<div>   \n<p>Hello\n there\n</p></div>");
        doc.outputSettings().prettyPrint(false);
        assertEquals("<html><head></head><body><div>   \n<p>Hello\n there\n</p></div></body></html>", doc.html());

        ElementImpl div = doc.select("div").first();
        assertEquals("   \n<p>Hello\n there\n</p>", div.html());
    }

    @Test
    public void testEmptyElementFormatHtml() {
        // don't put newlines into empty blocks
        DocumentImpl doc = Jsoup.parse("<section><div></div></section>");
        assertEquals("<section>\n <div></div>\n</section>", doc.select("section").first().outerHtml());
    }

    @Test
    public void testNoIndentOnScriptAndStyle() {
        // don't newline+indent closing </script> and </style> tags
        DocumentImpl doc = Jsoup.parse("<script>one\ntwo</script>\n<style>three\nfour</style>");
        assertEquals("<script>one\ntwo</script> \n<style>three\nfour</style>", doc.getHead().html());
    }

    @Test
    public void testContainerOutput() {
        DocumentImpl doc = Jsoup.parse("<title>Hello there</title> <div><p>Hello</p><p>there</p></div> <div>Another</div>");
        assertEquals("<title>Hello there</title>", doc.select("title").first().outerHtml());
        assertEquals("<div>\n <p>Hello</p>\n <p>there</p>\n</div>", doc.select("div").first().outerHtml());
        assertEquals("<div>\n <p>Hello</p>\n <p>there</p>\n</div> \n<div>\n Another\n</div>", doc.select("body").first().html());
    }

    @Test
    public void testSetText() {
        String h = "<div id=1>Hello <p>there <b>now</b></p></div>";
        DocumentImpl doc = Jsoup.parse(h);
        assertEquals("Hello there now", doc.text()); // need to sort out node whitespace
        assertEquals("there now", doc.select("p").get(0).text());

        ElementImpl div = doc.getElementById("1").text("Gone");
        assertEquals("Gone", div.text());
        assertEquals(0, doc.select("p").size());
    }

    @Test
    public void testAddNewElement() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.appendElement("p").text("there");
        div.appendElement("P").attr("CLASS", "second").text("now");
        // manually specifying tag and attributes should maintain case based on parser settings
        assertEquals("<html><head></head><body><div id=\"1\"><p>Hello</p><p>there</p><p class=\"second\">now</p></div></body></html>",
                TextUtil.stripNewlines(doc.html()));

        // check sibling index (with short circuit on reindexChildren):
        Elements ps = doc.select("p");
        for (int i = 0; i < ps.size(); i++) {
            assertEquals(i, ps.get(i).getSiblingIndex());
        }
    }

    @Test
    public void testAddBooleanAttribute() {
        ElementImpl div = new ElementImpl(Tag.valueOf("div"), "");

        div.attr("true", true);

        div.attr("false", "value");
        div.attr("false", false);

        assertTrue(div.hasAttr("true"));
        assertEquals("", div.attr("true"));

        List<AttributeModel> attributes = div.getAttributes().asList();
        assertEquals("There should be one attribute", 1, attributes.size());
        assertTrue("Attribute should be boolean", attributes.get(0) instanceof BooleanAttributeModel);

        assertFalse(div.hasAttr("false"));

        assertEquals("<div true></div>", div.outerHtml());
    }

    @Test
    public void testAppendRowToTable() {
        DocumentImpl doc = Jsoup.parse("<table><tr><td>1</td></tr></table>");
        ElementImpl table = doc.select("tbody").first();
        table.append("<tr><td>2</td></tr>");

        assertEquals("<table><tbody><tr><td>1</td></tr><tr><td>2</td></tr></tbody></table>", TextUtil.stripNewlines(doc.getBody().html()));
    }

    @Test
    public void testPrependRowToTable() {
        DocumentImpl doc = Jsoup.parse("<table><tr><td>1</td></tr></table>");
        ElementImpl table = doc.select("tbody").first();
        table.prepend("<tr><td>2</td></tr>");

        assertEquals("<table><tbody><tr><td>2</td></tr><tr><td>1</td></tr></tbody></table>", TextUtil.stripNewlines(doc.getBody().html()));

        // check sibling index (reindexChildren):
        Elements ps = doc.select("tr");
        for (int i = 0; i < ps.size(); i++) {
            assertEquals(i, ps.get(i).siblingIndex());
        }
    }

    @Test
    public void testPrependElement() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.prependElement("p").text("Before");
        assertEquals("Before", div.child(0).text());
        assertEquals("Hello", div.child(1).text());
    }

    @Test
    public void testAddNewText() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.appendText(" there & now >");
        assertEquals("<p>Hello</p> there &amp; now &gt;", TextUtil.stripNewlines(div.html()));
    }

    @Test
    public void testPrependText() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.prependText("there & now > ");
        assertEquals("there & now > Hello", div.text());
        assertEquals("there &amp; now &gt; <p>Hello</p>", TextUtil.stripNewlines(div.html()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnAddNullText() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.appendText(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnPrependNullText() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.prependText(null);
    }

    @Test
    public void testAddNewHtml() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.append("<p>there</p><p>now</p>");
        assertEquals("<p>Hello</p><p>there</p><p>now</p>", TextUtil.stripNewlines(div.html()));

        // check sibling index (no reindexChildren):
        Elements ps = doc.select("p");
        for (int i = 0; i < ps.size(); i++) {
            assertEquals(i, ps.get(i).getSiblingIndex());
        }
    }

    @Test
    public void testPrependNewHtml() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.prepend("<p>there</p><p>now</p>");
        assertEquals("<p>there</p><p>now</p><p>Hello</p>", TextUtil.stripNewlines(div.html()));

        // check sibling index (reindexChildren):
        Elements ps = doc.select("p");
        for (int i = 0; i < ps.size(); i++) {
            assertEquals(i, ps.get(i).getSiblingIndex());
        }
    }

    @Test
    public void testSetHtml() {
        DocumentImpl doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        ElementImpl div = doc.getElementById("1");
        div.html("<p>there</p><p>now</p>");
        assertEquals("<p>there</p><p>now</p>", TextUtil.stripNewlines(div.html()));
    }

    @Test
    public void testSetHtmlTitle() {
        DocumentImpl doc = Jsoup.parse("<html><head id=2><title id=1></title></head></html>");

        ElementImpl title = doc.getElementById("1");
        title.html("good");
        assertEquals("good", title.html());
        title.html("<i>bad</i>");
        assertEquals("&lt;i&gt;bad&lt;/i&gt;", title.html());

        ElementImpl head = doc.getElementById("2");
        head.html("<title><i>bad</i></title>");
        assertEquals("<title>&lt;i&gt;bad&lt;/i&gt;</title>", head.html());
    }

    @Test
    public void testWrap() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p><p>There</p></div>");
        ElementImpl p = doc.select("p").first();
        p.wrap("<div class='head'></div>");
        assertEquals("<div><div class=\"head\"><p>Hello</p></div><p>There</p></div>", TextUtil.stripNewlines(doc.getBody().html()));

        ElementImpl ret = p.wrap("<div><div class=foo></div><p>What?</p></div>");
        assertEquals("<div><div class=\"head\"><div><div class=\"foo\"><p>Hello</p></div><p>What?</p></div></div><p>There</p></div>",
                TextUtil.stripNewlines(doc.getBody().html()));

        assertEquals(ret, p);
    }

    @Test
    public void before() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p><p>There</p></div>");
        ElementImpl p1 = doc.select("p").first();
        p1.before("<div>one</div><div>two</div>");
        assertEquals("<div><div>one</div><div>two</div><p>Hello</p><p>There</p></div>", TextUtil.stripNewlines(doc.getBody().html()));

        doc.select("p").last().before("<p>Three</p><!-- four -->");
        assertEquals("<div><div>one</div><div>two</div><p>Hello</p><p>Three</p><!-- four --><p>There</p></div>", TextUtil.stripNewlines(doc.getBody().html()));
    }

    @Test
    public void after() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p><p>There</p></div>");
        ElementImpl p1 = doc.select("p").first();
        p1.after("<div>one</div><div>two</div>");
        assertEquals("<div><p>Hello</p><div>one</div><div>two</div><p>There</p></div>", TextUtil.stripNewlines(doc.getBody().html()));

        doc.select("p").last().after("<p>Three</p><!-- four -->");
        assertEquals("<div><p>Hello</p><div>one</div><div>two</div><p>There</p><p>Three</p><!-- four --></div>", TextUtil.stripNewlines(doc.getBody().html()));
    }

    @Test
    public void testWrapWithRemainder() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p></div>");
        ElementImpl p = doc.select("p").first();
        p.wrap("<div class='head'></div><p>There!</p>");
        assertEquals("<div><div class=\"head\"><p>Hello</p><p>There!</p></div></div>", TextUtil.stripNewlines(doc.getBody().html()));
    }

    @Test
    public void testHasText() {
        DocumentImpl doc = Jsoup.parse("<div><p>Hello</p><p></p></div>");
        ElementImpl div = doc.select("div").first();
        Elements ps = doc.select("p");

        assertTrue(div.hasText());
        assertTrue(ps.first().hasText());
        assertFalse(ps.last().hasText());
    }

    @Test
    public void dataset() {
        DocumentImpl doc = Jsoup.parse("<div id=1 data-name=jsoup class=new data-package=jar>Hello</div><p id=2>Hello</p>");
        ElementImpl div = doc.select("div").first();
        Map<String, String> dataset = div.dataset();
        AttributesModel attributes = div.getAttributes();

        // size, get, set, add, remove
        assertEquals(2, dataset.size());
        assertEquals("jsoup", dataset.get("name"));
        assertEquals("jar", dataset.get("package"));

        dataset.put("name", "jsoup updated");
        dataset.put("language", "java");
        dataset.remove("package");

        assertEquals(2, dataset.size());
        assertEquals(4, attributes.size());
        assertEquals("jsoup updated", attributes.get("data-name"));
        assertEquals("jsoup updated", dataset.get("name"));
        assertEquals("java", attributes.get("data-language"));
        assertEquals("java", dataset.get("language"));

        attributes.put("data-food", "bacon");
        assertEquals(3, dataset.size());
        assertEquals("bacon", dataset.get("food"));

        attributes.put("data-", "empty");
        assertEquals(null, dataset.get("")); // data- is not a data attribute

        ElementImpl p = doc.select("p").first();
        assertEquals(0, p.dataset().size());

    }

    @Test
    public void parentlessToString() {
        DocumentImpl doc = Jsoup.parse("<img src='foo'>");
        ElementImpl img = doc.select("img").first();
        assertEquals("<img src=\"foo\">", img.toString());

        img.remove(); // lost its parent
        assertEquals("<img src=\"foo\">", img.toString());
    }

    @Test
    public void testClone() {
        DocumentImpl doc = Jsoup.parse("<div><p>One<p><span>Two</div>");

        ElementImpl p = doc.select("p").get(1);
        ElementImpl clone = p.clone();

        assertNull(clone.parent()); // should be orphaned
        assertEquals(0, clone.getSiblingIndex());
        assertEquals(1, p.getSiblingIndex());
        assertNotNull(p.parent());

        clone.append("<span>Three");
        assertEquals("<p><span>Two</span><span>Three</span></p>", TextUtil.stripNewlines(clone.outerHtml()));
        assertEquals("<div><p>One</p><p><span>Two</span></p></div>", TextUtil.stripNewlines(doc.getBody().html())); // not modified

        doc.getBody().appendChild(clone); // adopt
        assertNotNull(clone.parent());
        assertEquals("<div><p>One</p><p><span>Two</span></p></div><p><span>Two</span><span>Three</span></p>", TextUtil.stripNewlines(doc.getBody().html()));
    }

    @Test
    public void testClonesClassnames() {
        DocumentImpl doc = Jsoup.parse("<div class='one two'></div>");
        ElementImpl div = doc.select("div").first();
        Set<String> classes = div.getClassNames();
        assertEquals(2, classes.size());
        assertTrue(classes.contains("one"));
        assertTrue(classes.contains("two"));

        ElementImpl copy = div.clone();
        Set<String> copyClasses = copy.getClassNames();
        assertEquals(2, copyClasses.size());
        assertTrue(copyClasses.contains("one"));
        assertTrue(copyClasses.contains("two"));
        copyClasses.add("three");
        copyClasses.remove("one");

        assertTrue(classes.contains("one"));
        assertFalse(classes.contains("three"));
        assertFalse(copyClasses.contains("one"));
        assertTrue(copyClasses.contains("three"));

        assertEquals("", div.html());
        assertEquals("", copy.html());
    }

    @Test
    public void testShallowClone() {
        String base = "http://example.com/";
        DocumentImpl doc = Jsoup.parse("<div id=1 class=one><p id=2 class=two>One", base);
        ElementImpl d = doc.selectFirst("div");
        ElementImpl p = doc.selectFirst("p");
        TextImpl t = p.textNodes().get(0);

        ElementImpl d2 = d.shallowClone();
        ElementImpl p2 = p.shallowClone();
        TextImpl t2 = (TextImpl) t.shallowClone();

        assertEquals(1, d.childNodeSize());
        assertEquals(0, d2.childNodeSize());

        assertEquals(1, p.childNodeSize());
        assertEquals(0, p2.childNodeSize());
        assertEquals("", p2.text());

        assertEquals("two", p2.className().get());
        assertEquals("One", t2.text());

        d2.append("<p id=3>Three");
        assertEquals(1, d2.childNodeSize());
        assertEquals("Three", d2.text());
        assertEquals("One", d.text());
        assertEquals(base, d2.baseUri());
    }

    @Test
    public void testTagNameSet() {
        DocumentImpl doc = Jsoup.parse("<div><i>Hello</i>");
        doc.select("i").first().tagName("em");
        assertEquals(0, doc.select("i").size());
        assertEquals(1, doc.select("em").size());
        assertEquals("<em>Hello</em>", doc.select("div").first().html());
    }

    @Test
    public void testHtmlContainsOuter() {
        DocumentImpl doc = Jsoup.parse("<title>Check</title> <div>Hello there</div>");
        doc.outputSettings().indentAmount(0);
        assertTrue(doc.html().contains(doc.select("title").outerHtml()));
        assertTrue(doc.html().contains(doc.select("div").outerHtml()));
    }

    @Test
    public void testGetTextNodes() {
        DocumentImpl doc = Jsoup.parse("<p>One <span>Two</span> Three <br> Four</p>");
        List<TextImpl> textNodes = doc.select("p").first().textNodes();

        assertEquals(3, textNodes.size());
        assertEquals("One ", textNodes.get(0).text());
        assertEquals(" Three ", textNodes.get(1).text());
        assertEquals(" Four", textNodes.get(2).text());

        assertEquals(0, doc.select("br").first().textNodes().size());
    }

    @Test
    public void testManipulateTextNodes() {
        DocumentImpl doc = Jsoup.parse("<p>One <span>Two</span> Three <br> Four</p>");
        ElementImpl p = doc.select("p").first();
        List<TextImpl> textNodes = p.textNodes();

        textNodes.get(1).text(" three-more ");
        textNodes.get(2).splitText(3).text("-ur");

        assertEquals("One Two three-more Fo-ur", p.text());
        assertEquals("One three-more Fo-ur", p.ownText());
        assertEquals(4, p.textNodes().size()); // grew because of split
    }

    @Test
    public void testGetDataNodes() {
        DocumentImpl doc = Jsoup.parse("<script>One Two</script> <style>Three Four</style> <p>Fix Six</p>");
        ElementImpl script = doc.select("script").first();
        ElementImpl style = doc.select("style").first();
        ElementImpl p = doc.select("p").first();

        List<DataImpl> scriptData = script.dataNodes();
        assertEquals(1, scriptData.size());
        assertEquals("One Two", scriptData.get(0).getWholeData());

        List<DataImpl> styleData = style.dataNodes();
        assertEquals(1, styleData.size());
        assertEquals("Three Four", styleData.get(0).getWholeData());

        List<DataImpl> pData = p.dataNodes();
        assertEquals(0, pData.size());
    }

    @Test
    public void elementIsNotASiblingOfItself() {
        DocumentImpl doc = Jsoup.parse("<div><p>One<p>Two<p>Three</div>");
        ElementImpl p2 = doc.select("p").get(1);

        assertEquals("Two", p2.text());
        Elements els = p2.siblingElements();
        assertEquals(2, els.size());
        assertEquals("<p>One</p>", els.get(0).outerHtml());
        assertEquals("<p>Three</p>", els.get(1).outerHtml());
    }

    @Test
    public void testChildThrowsIndexOutOfBoundsOnMissing() {
        DocumentImpl doc = Jsoup.parse("<div><p>One</p><p>Two</p></div>");
        ElementImpl div = doc.select("div").first();

        assertEquals(2, div.getChildren().size());
        assertEquals("One", div.child(0).text());

        try {
            div.child(3);
            fail("Should throw index out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void moveByAppend() {
        // test for https://github.com/jhy/jsoup/issues/239
        // can empty an element and append its children to another element
        DocumentImpl doc = Jsoup.parse("<div id=1>Text <p>One</p> Text <p>Two</p></div><div id=2></div>");
        ElementImpl div1 = doc.select("div").get(0);
        ElementImpl div2 = doc.select("div").get(1);

        assertEquals(4, div1.childNodeSize());
        List<NodeImpl> children = div1.getChildNodes();
        assertEquals(4, children.size());

        div2.insertChildren(0, children);

        assertEquals(0, children.size()); // children is backed by div1.childNodes, moved, so should be 0 now
        assertEquals(0, div1.childNodeSize());
        assertEquals(4, div2.childNodeSize());
        assertEquals("<div id=\"1\"></div>\n<div id=\"2\">\n Text \n <p>One</p> Text \n <p>Two</p>\n</div>",
                doc.getBody().html());
    }

    @Test
    public void insertChildrenArgumentValidation() {
        DocumentImpl doc = Jsoup.parse("<div id=1>Text <p>One</p> Text <p>Two</p></div><div id=2></div>");
        ElementImpl div1 = doc.select("div").get(0);
        ElementImpl div2 = doc.select("div").get(1);
        List<NodeImpl> children = div1.getChildNodes();

        try {
            div2.insertChildren(6, children);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            div2.insertChildren(-5, children);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            div2.insertChildren(0, (Collection<? extends NodeImpl>) null);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void insertChildrenAtPosition() {
        DocumentImpl doc = Jsoup.parse("<div id=1>Text1 <p>One</p> Text2 <p>Two</p></div><div id=2>Text3 <p>Three</p></div>");
        ElementImpl div1 = doc.select("div").get(0);
        Elements p1s = div1.select("p");
        ElementImpl div2 = doc.select("div").get(1);

        assertEquals(2, div2.childNodeSize());
        div2.insertChildren(-1, p1s);
        assertEquals(2, div1.childNodeSize()); // moved two out
        assertEquals(4, div2.childNodeSize());
        assertEquals(3, p1s.get(1).siblingIndex()); // should be last

        List<NodeImpl> els = new ArrayList<>();
        ElementImpl el1 = new ElementImpl(Tag.valueOf("span"), "").text("Span1");
        ElementImpl el2 = new ElementImpl(Tag.valueOf("span"), "").text("Span2");
        TextImpl tn1 = new TextImpl("Text4");
        els.add(el1);
        els.add(el2);
        els.add(tn1);

        assertNull(el1.parent());
        div2.insertChildren(-2, els);
        assertEquals(div2, el1.parent());
        assertEquals(7, div2.childNodeSize());
        assertEquals(3, el1.siblingIndex());
        assertEquals(4, el2.siblingIndex());
        assertEquals(5, tn1.siblingIndex());
    }

    @Test
    public void insertChildrenAsCopy() {
        DocumentImpl doc = Jsoup.parse("<div id=1>Text <p>One</p> Text <p>Two</p></div><div id=2></div>");
        ElementImpl div1 = doc.select("div").get(0);
        ElementImpl div2 = doc.select("div").get(1);
        Elements ps = doc.select("p").clone();
        ps.first().text("One cloned");
        div2.insertChildren(-1, ps);

        assertEquals(4, div1.childNodeSize()); // not moved -- cloned
        assertEquals(2, div2.childNodeSize());
        assertEquals("<div id=\"1\">Text <p>One</p> Text <p>Two</p></div><div id=\"2\"><p>One cloned</p><p>Two</p></div>",
                TextUtil.stripNewlines(doc.getBody().html()));
    }

    @Test
    public void testCssPath() {
        DocumentImpl doc = Jsoup.parse("<div id=\"id1\">A</div><div>B</div><div class=\"c1 c2\">C</div>");
        ElementImpl divA = doc.select("div").get(0);
        ElementImpl divB = doc.select("div").get(1);
        ElementImpl divC = doc.select("div").get(2);
        assertEquals(divA.cssSelector(), "#id1");
        assertEquals(divB.cssSelector(), "html > body > div:nth-child(2)");
        assertEquals(divC.cssSelector(), "html > body > div.c1.c2");

        assertTrue(divA == doc.select(divA.cssSelector()).first());
        assertTrue(divB == doc.select(divB.cssSelector()).first());
        assertTrue(divC == doc.select(divC.cssSelector()).first());
    }


    @Test
    public void testClassNames() {
        DocumentImpl doc = Jsoup.parse("<div class=\"c1 c2\">C</div>");
        ElementImpl div = doc.select("div").get(0);

        assertEquals("c1 c2", div.className().get());

        final Set<String> set1 = div.getClassNames();
        final Object[] arr1 = set1.toArray();
        assertTrue(arr1.length == 2);
        assertEquals("c1", arr1[0]);
        assertEquals("c2", arr1[1]);

        // Changes to the set should not be reflected in the Elements getters
        set1.add("c3");
        assertTrue(2 == div.getClassNames().size());
        assertEquals("c1 c2", div.className().get());

        // Update the class names to a fresh set
        final Set<String> newSet = new LinkedHashSet<>(3);
        newSet.addAll(set1);
        newSet.add("c3");

        div.setClassNames(newSet);


        assertEquals("c1 c2 c3", div.className().get());

        final Set<String> set2 = div.getClassNames();
        final Object[] arr2 = set2.toArray();
        assertTrue(arr2.length == 3);
        assertEquals("c1", arr2[0]);
        assertEquals("c2", arr2[1]);
        assertEquals("c3", arr2[2]);
    }

    @Test
    public void testHashAndEqualsAndValue() {
        // .equals and hashcode are identity. value is content.

        String doc1 = "<div id=1><p class=one>One</p><p class=one>One</p><p class=one>Two</p><p class=two>One</p></div>" +
                "<div id=2><p class=one>One</p><p class=one>One</p><p class=one>Two</p><p class=two>One</p></div>";

        DocumentImpl doc = Jsoup.parse(doc1);
        Elements els = doc.select("p");

        /*
        for (Element el : els) {
            System.out.println(el.hashCode() + " - " + el.outerHtml());
        }

        0 1534787905 - <p class="one">One</p>
        1 1534787905 - <p class="one">One</p>
        2 1539683239 - <p class="one">Two</p>
        3 1535455211 - <p class="two">One</p>
        4 1534787905 - <p class="one">One</p>
        5 1534787905 - <p class="one">One</p>
        6 1539683239 - <p class="one">Two</p>
        7 1535455211 - <p class="two">One</p>
        */
        assertEquals(8, els.size());
        ElementImpl e0 = els.get(0);
        ElementImpl e1 = els.get(1);
        ElementImpl e2 = els.get(2);
        ElementImpl e3 = els.get(3);
        ElementImpl e4 = els.get(4);
        ElementImpl e5 = els.get(5);
        ElementImpl e6 = els.get(6);
        ElementImpl e7 = els.get(7);

        assertEquals(e0, e0);
        assertTrue(e0.hasSameValue(e1));
        assertTrue(e0.hasSameValue(e4));
        assertTrue(e0.hasSameValue(e5));
        assertFalse(e0.equals(e2));
        assertFalse(e0.hasSameValue(e2));
        assertFalse(e0.hasSameValue(e3));
        assertFalse(e0.hasSameValue(e6));
        assertFalse(e0.hasSameValue(e7));

        assertEquals(e0.hashCode(), e0.hashCode());
        assertFalse(e0.hashCode() == (e2.hashCode()));
        assertFalse(e0.hashCode() == (e3).hashCode());
        assertFalse(e0.hashCode() == (e6).hashCode());
        assertFalse(e0.hashCode() == (e7).hashCode());
    }

    @Test
    public void testRelativeUrls() {
        String html = "<body><a href='./one.html'>One</a> <a href='two.html'>two</a> <a href='../three.html'>Three</a> <a href='//example2.com/four/'>Four</a> <a href='https://example2.com/five/'>Five</a>";
        DocumentImpl doc = Jsoup.parse(html, "http://example.com/bar/");
        Elements els = doc.select("a");

        assertEquals("http://example.com/bar/one.html", els.get(0).absUrl("href"));
        assertEquals("http://example.com/bar/two.html", els.get(1).absUrl("href"));
        assertEquals("http://example.com/three.html", els.get(2).absUrl("href"));
        assertEquals("http://example2.com/four/", els.get(3).absUrl("href"));
        assertEquals("https://example2.com/five/", els.get(4).absUrl("href"));
    }

    @Test
    public void appendMustCorrectlyMoveChildrenInsideOneParentElement() {
        DocumentImpl doc = new DocumentImpl("");
        ElementImpl body = doc.appendElement("body");
        body.appendElement("div1");
        body.appendElement("div2");
        final ElementImpl div3 = body.appendElement("div3");
        div3.text("Check");
        final ElementImpl div4 = body.appendElement("div4");

        ArrayList<ElementImpl> toMove = new ArrayList<>();
        toMove.add(div3);
        toMove.add(div4);

        body.insertChildren(0, toMove);

        String result = doc.toString().replaceAll("\\s+", "");
        assertEquals("<body><div3>Check</div3><div4></div4><div1></div1><div2></div2></body>", result);
    }

    @Test
    public void testHashcodeIsStableWithContentChanges() {
        ElementImpl root = new ElementImpl(Tag.valueOf("root"), "");

        HashSet<ElementImpl> set = new HashSet<>();
        // Add root node:
        set.add(root);

        root.appendChild(new ElementImpl(Tag.valueOf("a"), ""));
        assertTrue(set.contains(root));
    }

    @Test
    public void testNamespacedElements() {
        // Namespaces with ns:tag in HTML must be translated to ns|tag in CSS.
        String html = "<html><body><fb:comments /></body></html>";
        DocumentImpl doc = Jsoup.parse(html, "http://example.com/bar/");
        Elements els = doc.select("fb|comments");
        assertEquals(1, els.size());
        assertEquals("html > body > fb|comments", els.get(0).cssSelector());
    }

    @Test
    public void testChainedRemoveAttributes() {
        String html = "<a one two three four>Text</a>";
        DocumentImpl doc = Jsoup.parse(html);
        ElementImpl a = doc.select("a").first();
        a
                .removeAttr("zero")
                .removeAttr("one")
                .removeAttr("two")
                .removeAttr("three")
                .removeAttr("four")
                .removeAttr("five");
        assertEquals("<a>Text</a>", a.outerHtml());
    }

    @Test
    public void testLoopedRemoveAttributes() {
        String html = "<a one two three four>Text</a><p foo>Two</p>";
        DocumentImpl doc = Jsoup.parse(html);
        for (ElementImpl el : doc.getAllElements()) {
            el.clearAttributes();
        }

        assertEquals("<a>Text</a>\n<p>Two</p>", doc.getBody().html());
    }

    @Test
    public void testIs() {
        String html = "<div><p>One <a class=big>Two</a> Three</p><p>Another</p>";
        DocumentImpl doc = Jsoup.parse(html);
        ElementImpl p = doc.select("p").first();

        assertTrue(p.is("p"));
        assertFalse(p.is("div"));
        assertTrue(p.is("p:has(a)"));
        assertTrue(p.is("p:first-child"));
        assertFalse(p.is("p:last-child"));
        assertTrue(p.is("*"));
        assertTrue(p.is("div p"));

        ElementImpl q = doc.select("p").last();
        assertTrue(q.is("p"));
        assertTrue(q.is("p ~ p"));
        assertTrue(q.is("p + p"));
        assertTrue(q.is("p:last-child"));
        assertFalse(q.is("p a"));
        assertFalse(q.is("a"));
    }


    @Test
    public void elementByTagName() {
        ElementImpl a = new ElementImpl("P");
        assertTrue(a.tagName().equals("P"));
    }

    @Test
    public void testChildrenElements() {
        String html = "<div><p><a>One</a></p><p><a>Two</a></p>Three</div><span>Four</span><foo></foo><img>";
        DocumentImpl doc = Jsoup.parse(html);
        ElementImpl div = doc.select("div").first();
        ElementImpl p = doc.select("p").first();
        ElementImpl span = doc.select("span").first();
        ElementImpl foo = doc.select("foo").first();
        ElementImpl img = doc.select("img").first();

        Elements docChildren = div.getChildren();
        assertEquals(2, docChildren.size());
        assertEquals("<p><a>One</a></p>", docChildren.get(0).outerHtml());
        assertEquals("<p><a>Two</a></p>", docChildren.get(1).outerHtml());
        assertEquals(3, div.getChildNodes().size());
        assertEquals("Three", div.getChildNodes().get(2).outerHtml());

        assertEquals(1, p.getChildren().size());
        assertEquals("One", p.getChildren().text());

        assertEquals(0, span.getChildren().size());
        assertEquals(1, span.getChildNodes().size());
        assertEquals("Four", span.getChildNodes().get(0).outerHtml());

        assertEquals(0, foo.getChildren().size());
        assertEquals(0, foo.getChildNodes().size());
        assertEquals(0, img.getChildren().size());
        assertEquals(0, img.getChildNodes().size());
    }

    @Test
    public void testShadowElementsAreUpdated() {
        String html = "<div><p><a>One</a></p><p><a>Two</a></p>Three</div><span>Four</span><foo></foo><img>";
        DocumentImpl doc = Jsoup.parse(html);
        ElementImpl div = doc.select("div").first();
        Elements els = div.getChildren();
        List<NodeImpl> nodes = div.getChildNodes();

        assertEquals(2, els.size()); // the two Ps
        assertEquals(3, nodes.size()); // the "Three" textnode

        ElementImpl p3 = new ElementImpl("p").text("P3");
        ElementImpl p4 = new ElementImpl("p").text("P4");
        div.insertChildren(1, p3);
        div.insertChildren(3, p4);
        Elements els2 = div.getChildren();

        // first els should not have changed
        assertEquals(2, els.size());
        assertEquals(4, els2.size());

        assertEquals("<p><a>One</a></p>\n" +
                "<p>P3</p>\n" +
                "<p><a>Two</a></p>\n" +
                "<p>P4</p>Three", div.html());
        assertEquals("P3", els2.get(1).text());
        assertEquals("P4", els2.get(3).text());

        p3.after("<span>Another</span");

        Elements els3 = div.getChildren();
        assertEquals(5, els3.size());
        assertEquals("span", els3.get(2).tagName());
        assertEquals("Another", els3.get(2).text());

        assertEquals("<p><a>One</a></p>\n" +
                "<p>P3</p>\n" +
                "<span>Another</span>\n" +
                "<p><a>Two</a></p>\n" +
                "<p>P4</p>Three", div.html());
    }

    @Test
    public void classNamesAndAttributeNameIsCaseInsensitive() {
        String html = "<p Class='SomeText AnotherText'>One</p>";
        DocumentImpl doc = Jsoup.parse(html);
        ElementImpl p = doc.select("p").first();
        assertEquals("SomeText AnotherText", p.className().get());
        assertTrue(p.getClassNames().contains("SomeText"));
        assertTrue(p.getClassNames().contains("AnotherText"));
        assertTrue(p.hasClass("SomeText"));
        assertTrue(p.hasClass("sometext"));
        assertTrue(p.hasClass("AnotherText"));
        assertTrue(p.hasClass("anothertext"));

        ElementImpl p1 = doc.select(".SomeText").first();
        ElementImpl p2 = doc.select(".sometext").first();
        ElementImpl p3 = doc.select("[class=SomeText AnotherText]").first();
        ElementImpl p4 = doc.select("[Class=SomeText AnotherText]").first();
        ElementImpl p5 = doc.select("[class=sometext anothertext]").first();
        ElementImpl p6 = doc.select("[class=SomeText AnotherText]").first();
        ElementImpl p7 = doc.select("[class^=sometext]").first();
        ElementImpl p8 = doc.select("[class$=nothertext]").first();
        ElementImpl p9 = doc.select("[class^=sometext]").first();
        ElementImpl p10 = doc.select("[class$=AnotherText]").first();

        assertEquals("One", p1.text());
        assertEquals(p1, p2);
        assertEquals(p1, p3);
        assertEquals(p1, p4);
        assertEquals(p1, p5);
        assertEquals(p1, p6);
        assertEquals(p1, p7);
        assertEquals(p1, p8);
        assertEquals(p1, p9);
        assertEquals(p1, p10);
    }

    @Test
    public void testAppendTo() {
        String parentHtml = "<div class='a'></div>";
        String childHtml = "<div class='b'></div><p>Two</p>";

        DocumentImpl parentDoc = Jsoup.parse(parentHtml);
        ElementImpl parent = parentDoc.getBody();
        DocumentImpl childDoc = Jsoup.parse(childHtml);

        ElementImpl div = childDoc.select("div").first();
        ElementImpl p = childDoc.select("p").first();
        ElementImpl appendTo1 = div.appendTo(parent);
        assertEquals(div, appendTo1);

        ElementImpl appendTo2 = p.appendTo(div);
        assertEquals(p, appendTo2);

        assertEquals("<div class=\"a\"></div>\n<div class=\"b\">\n <p>Two</p>\n</div>", parentDoc.getBody().html());
        assertEquals("", childDoc.getBody().html()); // got moved out
    }

    @Test
    public void testNormalizesNbspInText() {
        String escaped = "You can't always get what you&nbsp;want.";
        String withNbsp = "You can't always get what you want."; // there is an nbsp char in there
        DocumentImpl doc = Jsoup.parse("<p>" + escaped);
        ElementImpl p = doc.select("p").first();
        assertEquals("You can't always get what you want.", p.text()); // text is normalized

        assertEquals("<p>" + escaped + "</p>", p.outerHtml()); // html / whole text keeps &nbsp;
        assertEquals(withNbsp, p.textNodes().get(0).getWholeText());
        assertEquals(160, withNbsp.charAt(29));

        ElementImpl matched = doc.select("p:contains(get what you want)").first();
        assertEquals("p", matched.nodeName());
        assertTrue(matched.is(":containsOwn(get what you want)"));
    }

    @Test
    public void testNormalizesInvisiblesInText() {
        // return Character.getType(c) == 16 && (c == 8203 || c == 8204 || c == 8205 || c == 173);
        String escaped = "This&shy;is&#x200b;one&#x200c;long&#x200d;word";
        String decoded = "This\u00ADis\u200Bone\u200Clong\u200Dword"; // browser would not display those soft hyphens / other chars, so we don't want them in the text

        DocumentImpl doc = Jsoup.parse("<p>" + escaped);
        ElementImpl p = doc.select("p").first();
        doc.outputSettings().charset("ascii"); // so that the outer html is easier to see with escaped invisibles
        assertEquals("Thisisonelongword", p.text()); // text is normalized
        assertEquals("<p>" + escaped + "</p>", p.outerHtml()); // html / whole text keeps &shy etc;
        assertEquals(decoded, p.textNodes().get(0).getWholeText());

        ElementImpl matched = doc.select("p:contains(Thisisonelongword)").first(); // really just oneloneword, no invisibles
        assertEquals("p", matched.nodeName());
        assertTrue(matched.is(":containsOwn(Thisisonelongword)"));

    }

    @Test
    public void testRemoveBeforeIndex() {
        DocumentImpl doc = Jsoup.parse(
                "<html><body><div><p>before1</p><p>before2</p><p>XXX</p><p>after1</p><p>after2</p></div></body></html>",
                "");
        ElementImpl body = doc.select("body").first();
        Elements elems = body.select("p:matchesOwn(XXX)");
        ElementImpl xElem = elems.first();
        Elements beforeX = xElem.parent().getElementsByIndexLessThan(xElem.elementSiblingIndex());

        for (ElementImpl p : beforeX) {
            p.remove();
        }

        assertEquals("<body><div><p>XXX</p><p>after1</p><p>after2</p></div></body>", TextUtil.stripNewlines(body.outerHtml()));
    }

    @Test
    public void testRemoveAfterIndex() {
        DocumentImpl doc2 = Jsoup.parse(
                "<html><body><div><p>before1</p><p>before2</p><p>XXX</p><p>after1</p><p>after2</p></div></body></html>",
                "");
        ElementImpl body = doc2.select("body").first();
        Elements elems = body.select("p:matchesOwn(XXX)");
        ElementImpl xElem = elems.first();
        Elements afterX = xElem.parent().getElementsByIndexGreaterThan(xElem.elementSiblingIndex());

        for (ElementImpl p : afterX) {
            p.remove();
        }

        assertEquals("<body><div><p>before1</p><p>before2</p><p>XXX</p></div></body>", TextUtil.stripNewlines(body.outerHtml()));
    }

    @Test
    public void whiteSpaceClassElement() {
        Tag tag = Tag.valueOf("a");
        AttributesModel attribs = new AttributesModel();
        ElementImpl el = new ElementImpl(tag, "", attribs);

        attribs.put("class", "abc ");
        boolean hasClass = el.hasClass("ab");
        assertFalse(hasClass);
    }

    @Test
    public void testNextElementSiblingAfterClone() {
        // via https://github.com/jhy/jsoup/issues/951
        String html = "<!DOCTYPE html><html lang=\"en\"><head></head><body><div>Initial element</div></body></html>";
        String expectedText = "New element";
        String cloneExpect = "New element in clone";

        DocumentImpl original = Jsoup.parse(html);
        DocumentImpl clone = (DocumentImpl) original .clone();

        ElementImpl originalElement = original.getBody().child(0);
        originalElement.after("<div>" + expectedText + "</div>");
        ElementImpl originalNextElementSibling = originalElement.nextElementSibling();
        ElementImpl originalNextSibling = (ElementImpl) originalElement.nextSibling();
        assertEquals(expectedText, originalNextElementSibling.text());
        assertEquals(expectedText, originalNextSibling.text());

        ElementImpl cloneElement = clone.getBody().child(0);
        cloneElement.after("<div>" + cloneExpect + "</div>");
        ElementImpl cloneNextElementSibling = cloneElement.nextElementSibling();
        ElementImpl cloneNextSibling = (ElementImpl) cloneElement.nextSibling();
        assertEquals(cloneExpect, cloneNextElementSibling.text());
        assertEquals(cloneExpect, cloneNextSibling.text());
    }

    @Test
    public void testRemovingEmptyClassAttributeWhenLastClassRemoved() {
        // https://github.com/jhy/jsoup/issues/947
        DocumentImpl doc = Jsoup.parse("<img class=\"one two\" />");
        ElementImpl img = doc.select("img").first();
        img.removeClass("one");
        img.removeClass("two");
        assertFalse(doc.getBody().html().contains("class=\"\""));
    }

    @Test
    public void booleanAttributeOutput() {
        DocumentImpl doc = Jsoup.parse("<img src=foo noshade='' nohref async=async autofocus=false>");
        ElementImpl img = doc.selectFirst("img");

        assertEquals("<img src=\"foo\" noshade nohref async autofocus=\"false\">", img.outerHtml());
    }

    @Test
    public void textHasSpaceAfterBlockTags() {
        DocumentImpl doc = Jsoup.parse("<div>One</div>Two");
        assertEquals("One Two", doc.text());
    }

    @Test
    public void testNextElementSiblings() {
        DocumentImpl doc = Jsoup.parse("<ul id='ul'>" +
                "<li id='a'>a</li>" +
                "<li id='b'>b</li>" +
                "<li id='c'>c</li>" +
                "</ul> Not An Element but a node" +
                "<div id='div'>" +
                "<li id='d'>d</li>" +
                "</div>");

        ElementImpl element = doc.getElementById("a");
        Elements elementSiblings = element.nextElementSiblings();
        assertNotNull(elementSiblings);
        assertEquals(2, elementSiblings.size());
        assertEquals("b", elementSiblings.get(0).id().get());
        assertEquals("c", elementSiblings.get(1).id().get());

        ElementImpl element1 = doc.getElementById("b");
        List<ElementImpl> elementSiblings1 = element1.nextElementSiblings();
        assertNotNull(elementSiblings1);
        assertEquals(1, elementSiblings1.size());
        assertEquals("c", elementSiblings1.get(0).id().get());

        ElementImpl element2 = doc.getElementById("c");
        List<ElementImpl> elementSiblings2 = element2.nextElementSiblings();
        assertEquals(0, elementSiblings2.size());

        ElementImpl ul = doc.getElementById("ul");
        List<ElementImpl> elementSiblings3 = ul.nextElementSiblings();
        assertNotNull(elementSiblings3);
        assertEquals(1, elementSiblings3.size());
        assertEquals("div", elementSiblings3.get(0).id().get());

        ElementImpl div = doc.getElementById("div");
        List<ElementImpl> elementSiblings4 = div.nextElementSiblings();
        assertEquals(0, elementSiblings4.size());
    }

    @Test
    public void testPreviousElementSiblings() {
        DocumentImpl doc = Jsoup.parse("<ul id='ul'>" +
                "<li id='a'>a</li>" +
                "<li id='b'>b</li>" +
                "<li id='c'>c</li>" +
                "</ul>" +
                "<div id='div'>" +
                "<li id='d'>d</li>" +
                "</div>");

        ElementImpl element = doc.getElementById("b");
        Elements elementSiblings = element.previousElementSiblings();
        assertNotNull(elementSiblings);
        assertEquals(1, elementSiblings.size());
        assertEquals("a", elementSiblings.get(0).id().get());

        ElementImpl element1 = doc.getElementById("a");
        List<ElementImpl> elementSiblings1 = element1.previousElementSiblings();
        assertEquals(0, elementSiblings1.size());

        ElementImpl element2 = doc.getElementById("c");
        List<ElementImpl> elementSiblings2 = element2.previousElementSiblings();
        assertNotNull(elementSiblings2);
        assertEquals(2, elementSiblings2.size());
        assertEquals("b", elementSiblings2.get(0).id().get());
        assertEquals("a", elementSiblings2.get(1).id().get());

        ElementImpl ul = doc.getElementById("ul");
        List<ElementImpl> elementSiblings3 = ul.previousElementSiblings();
        assertEquals(0, elementSiblings3.size());
    }
}
