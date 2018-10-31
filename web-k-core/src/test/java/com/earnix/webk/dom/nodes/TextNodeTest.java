package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.TextUtil;
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
        TextNodeModel one = new TextNodeModel("");
        TextNodeModel two = new TextNodeModel("     ");
        TextNodeModel three = new TextNodeModel("  \n\n   ");
        TextNodeModel four = new TextNodeModel("Hello");
        TextNodeModel five = new TextNodeModel("  \nHello ");

        assertTrue(one.isBlank());
        assertTrue(two.isBlank());
        assertTrue(three.isBlank());
        assertFalse(four.isBlank());
        assertFalse(five.isBlank());
    }

    @Test
    public void testTextBean() {
        DocumentModel doc = Jsoup.parse("<p>One <span>two &amp;</span> three &amp;</p>");
        ElementModel p = doc.select("p").first();

        ElementModel span = doc.select("span").first();
        assertEquals("two &", span.text());
        TextNodeModel spanText = (TextNodeModel) span.childNode(0);
        assertEquals("two &", spanText.text());

        TextNodeModel tn = (TextNodeModel) p.childNode(2);
        assertEquals(" three &", tn.text());

        tn.text(" POW!");
        Assert.assertEquals("One <span>two &amp;</span> POW!", TextUtil.stripNewlines(p.html()));

        tn.attr(tn.nodeName(), "kablam &");
        assertEquals("kablam &", tn.text());
        assertEquals("One <span>two &amp;</span>kablam &amp;", TextUtil.stripNewlines(p.html()));
    }

    @Test
    public void testSplitText() {
        DocumentModel doc = Jsoup.parse("<div>Hello there</div>");
        ElementModel div = doc.select("div").first();
        TextNodeModel tn = (TextNodeModel) div.childNode(0);
        TextNodeModel tail = tn.splitText(6);
        assertEquals("Hello ", tn.getWholeText());
        assertEquals("there", tail.getWholeText());
        tail.text("there!");
        assertEquals("Hello there!", div.text());
        assertTrue(tn.parent() == tail.parent());
    }

    @Test
    public void testSplitAnEmbolden() {
        DocumentModel doc = Jsoup.parse("<div>Hello there</div>");
        ElementModel div = doc.select("div").first();
        TextNodeModel tn = (TextNodeModel) div.childNode(0);
        TextNodeModel tail = tn.splitText(6);
        tail.wrap("<b></b>");

        assertEquals("Hello <b>there</b>", TextUtil.stripNewlines(div.html())); // not great that we get \n<b>there there... must correct
    }

    @Test
    public void testWithSupplementaryCharacter() {
        DocumentModel doc = Jsoup.parse(new String(Character.toChars(135361)));
        TextNodeModel t = doc.body().textNodes().get(0);
        assertEquals(new String(Character.toChars(135361)), t.outerHtml().trim());
    }

    @Test
    public void testLeadNodesHaveNoChildren() {
        DocumentModel doc = Jsoup.parse("<div>Hello there</div>");
        ElementModel div = doc.select("div").first();
        TextNodeModel tn = (TextNodeModel) div.childNode(0);
        List<NodeModel> nodes = tn.childNodes();
        assertEquals(0, nodes.size());
    }
}
