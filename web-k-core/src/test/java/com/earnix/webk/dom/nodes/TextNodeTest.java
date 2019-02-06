package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.TextUtil;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.script.whatwg_dom.impl.DocumentImpl;
import com.earnix.webk.script.whatwg_dom.impl.TextImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test TextNodes
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class TextNodeTest {
    @Test
    public void testBlank() {
        TextImpl one = new TextImpl("");
        TextImpl two = new TextImpl("     ");
        TextImpl three = new TextImpl("  \n\n   ");
        TextImpl four = new TextImpl("Hello");
        TextImpl five = new TextImpl("  \nHello ");

        assertTrue(one.isBlank());
        assertTrue(two.isBlank());
        assertTrue(three.isBlank());
        assertFalse(four.isBlank());
        assertFalse(five.isBlank());
    }

    @Test
    public void testTextBean() {
        DocumentImpl doc = Jsoup.parse("<p>One <span>two &amp;</span> three &amp;</p>");
        ElementImpl p = doc.select("p").first();

        ElementImpl span = doc.select("span").first();
        assertEquals("two &", span.text());
        TextImpl spanText = (TextImpl) span.childNode(0);
        assertEquals("two &", spanText.text());

        TextImpl tn = (TextImpl) p.childNode(2);
        assertEquals(" three &", tn.text());

        tn.text(" POW!");
        Assert.assertEquals("One <span>two &amp;</span> POW!", TextUtil.stripNewlines(p.html()));

        tn.attr(tn.nodeName(), "kablam &");
        assertEquals("kablam &", tn.text());
        assertEquals("One <span>two &amp;</span>kablam &amp;", TextUtil.stripNewlines(p.html()));
    }

    @Test
    public void testSplitText() {
        DocumentImpl doc = Jsoup.parse("<div>Hello there</div>");
        ElementImpl div = doc.select("div").first();
        TextImpl tn = (TextImpl) div.childNode(0);
        TextImpl tail = tn.splitText(6);
        assertEquals("Hello ", tn.getWholeText());
        assertEquals("there", tail.getWholeText());
        tail.text("there!");
        assertEquals("Hello there!", div.text());
        assertTrue(tn.parent() == tail.parent());
    }

    @Test
    public void testSplitAnEmbolden() {
        DocumentImpl doc = Jsoup.parse("<div>Hello there</div>");
        ElementImpl div = doc.select("div").first();
        TextImpl tn = (TextImpl) div.childNode(0);
        TextImpl tail = tn.splitText(6);
        tail.wrap("<b></b>");

        assertEquals("Hello <b>there</b>", TextUtil.stripNewlines(div.html())); // not great that we get \n<b>there there... must correct
    }

    @Test
    public void testWithSupplementaryCharacter() {
        DocumentImpl doc = Jsoup.parse(new String(Character.toChars(135361)));
        TextImpl t = doc.getBody().textNodes().get(0);
        assertEquals(new String(Character.toChars(135361)), t.outerHtml().trim());
    }

    @Test
    public void testLeadNodesHaveNoChildren() {
        DocumentImpl doc = Jsoup.parse("<div>Hello there</div>");
        ElementImpl div = doc.select("div").first();
        TextImpl tn = (TextImpl) div.childNode(0);
        List<NodeImpl> nodes = tn.getChildNodes();
        assertEquals(0, nodes.size());
    }
}
