package com.earnix.webk.runtime.whatwg_dom.impl.parser;

import com.earnix.webk.runtime.whatwg_dom.impl.Jsoup;
import com.earnix.webk.runtime.whatwg_dom.impl.nodes.AttributeModel;
import com.earnix.webk.runtime.whatwg_dom.impl.nodes.AttributesModel;
import com.earnix.webk.runtime.whatwg_dom.impl.nodes.BooleanAttributeModel;
import com.earnix.webk.runtime.whatwg_dom.impl.select.Elements;
import com.earnix.webk.runtime.html.impl.DocumentImpl;
import com.earnix.webk.runtime.impl.ElementImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test suite for attribute parser.
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class AttributeParseTest {

    @Test
    public void parsesRoughAttributeString() {
        String html = "<a id=\"123\" class=\"baz = 'bar'\" style = 'border: 2px'qux zim foo = 12 mux=18 />";
        // should be: <id=123>, <class=baz = 'bar'>, <qux=>, <zim=>, <foo=12>, <mux.=18>

        ElementImpl el = Jsoup.parse(html).getElementsByTag("a").get(0);
        AttributesModel attr = el.getAttributes();
        assertEquals(7, attr.size());
        assertEquals("123", attr.get("id"));
        assertEquals("baz = 'bar'", attr.get("class"));
        assertEquals("border: 2px", attr.get("style"));
        assertEquals("", attr.get("qux"));
        assertEquals("", attr.get("zim"));
        assertEquals("12", attr.get("foo"));
        assertEquals("18", attr.get("mux"));
    }

    @Test
    public void handlesNewLinesAndReturns() {
        String html = "<a\r\nfoo='bar\r\nqux'\r\nbar\r\n=\r\ntwo>One</a>";
        ElementImpl el = Jsoup.parse(html).select("a").first();
        assertEquals(2, el.getAttributes().size());
        assertEquals("bar\r\nqux", el.attr("foo")); // currently preserves newlines in quoted attributes. todo confirm if should.
        assertEquals("two", el.attr("bar"));
    }

    @Test
    public void parsesEmptyString() {
        String html = "<a />";
        ElementImpl el = Jsoup.parse(html).getElementsByTag("a").get(0);
        AttributesModel attr = el.getAttributes();
        assertEquals(0, attr.size());
    }

    @Test
    public void canStartWithEq() {
        String html = "<a =empty />";
        ElementImpl el = Jsoup.parse(html).getElementsByTag("a").get(0);
        AttributesModel attr = el.getAttributes();
        assertEquals(1, attr.size());
        assertTrue(attr.hasKey("=empty"));
        assertEquals("", attr.get("=empty"));
    }

    @Test
    public void strictAttributeUnescapes() {
        String html = "<a id=1 href='?foo=bar&mid&lt=true'>One</a> <a id=2 href='?foo=bar&lt;qux&lg=1'>Two</a>";
        Elements els = Jsoup.parse(html).select("a");
        assertEquals("?foo=bar&mid&lt=true", els.first().attr("href"));
        assertEquals("?foo=bar<qux&lg=1", els.last().attr("href"));
    }

    @Test
    public void moreAttributeUnescapes() {
        String html = "<a href='&wr_id=123&mid-size=true&ok=&wr'>Check</a>";
        Elements els = Jsoup.parse(html).select("a");
        assertEquals("&wr_id=123&mid-size=true&ok=&wr", els.first().attr("href"));
    }

    @Test
    public void parsesBooleangetAttributes() {
        String html = "<a normal=\"123\" boolean empty=\"\"></a>";
        ElementImpl el = Jsoup.parse(html).select("a").first();

        assertEquals("123", el.attr("normal"));
        assertEquals("", el.attr("boolean"));
        assertEquals("", el.attr("empty"));

        List<AttributeModel> attributes = el.getAttributes().asList();
        assertEquals("There should be 3 attribute present", 3, attributes.size());

        // Assuming the list order always follows the parsed html
        assertFalse("'normal' attribute should not be boolean", attributes.get(0) instanceof BooleanAttributeModel);
        assertTrue("'boolean' attribute should be boolean", attributes.get(1) instanceof BooleanAttributeModel);
        assertFalse("'empty' attribute should not be boolean", attributes.get(2) instanceof BooleanAttributeModel);

        assertEquals(html, el.outerHtml());
    }

    @Test
    public void dropsSlashFromAttributeName() {
        String html = "<img /onerror='doMyJob'/>";
        DocumentImpl doc = Jsoup.parse(html);
        assertTrue("SelfClosingStartTag ignores last character", doc.select("img[onerror]").size() != 0);
        assertEquals("<img onerror=\"doMyJob\">", doc.getBody().html());

        doc = Jsoup.parse(html, "", Parser.xmlParser());
        assertEquals("<img onerror=\"doMyJob\" />", doc.html());
    }
}
