package com.earnix.webk.dom.parser;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.nodes.AttributeModel;
import com.earnix.webk.dom.nodes.CommentModel;
import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.TextNodeModel;
import com.earnix.webk.dom.select.Elements;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static com.earnix.webk.dom.parser.CharacterReader.maxBufferLen;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TokeniserTest {
    @Test
    public void bufferUpInAttributeVal() {
        // https://github.com/jhy/jsoup/issues/967

        // check each double, singlem, unquoted impls
        String[] quotes = {"\"", "'", ""};
        for (String quote : quotes) {
            String preamble = "<img src=" + quote;
            String tail = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
            StringBuilder sb = new StringBuilder(preamble);

            final int charsToFillBuffer = maxBufferLen - preamble.length();
            for (int i = 0; i < charsToFillBuffer; i++) {
                sb.append('a');
            }

            sb.append('X'); // First character to cross character buffer boundary
            sb.append(tail + quote + ">\n");

            String html = sb.toString();
            DocumentModel doc = Jsoup.parse(html);
            String src = doc.select("img").attr("src");

            assertTrue("Handles for quote " + quote, src.contains("X"));
            assertTrue(src.contains(tail));
        }
    }

    @Test
    public void handleSuperLargeTagNames() {
        // unlikely, but valid. so who knows.

        StringBuilder sb = new StringBuilder(maxBufferLen);
        do {
            sb.append("LargeTagName");
        } while (sb.length() < maxBufferLen);
        String tag = sb.toString();
        String html = "<" + tag + ">One</" + tag + ">";

        DocumentModel doc = Parser.htmlParser().settings(ParseSettings.preserveCase).parseInput(html, "");
        Elements els = doc.select(tag);
        assertEquals(1, els.size());
        ElementModel el = els.first();
        assertNotNull(el);
        assertEquals("One", el.text());
        assertEquals(tag, el.tagName());
    }

    @Test
    public void handleSuperLargeAttributeName() {
        StringBuilder sb = new StringBuilder(maxBufferLen);
        do {
            sb.append("LargAttributeName");
        } while (sb.length() < maxBufferLen);
        String attrName = sb.toString();
        String html = "<p " + attrName + "=foo>One</p>";

        DocumentModel doc = Jsoup.parse(html);
        Elements els = doc.getElementsByAttribute(attrName);
        assertEquals(1, els.size());
        ElementModel el = els.first();
        assertNotNull(el);
        assertEquals("One", el.text());
        AttributeModel attribute = el.attributes().asList().get(0);
        assertEquals(attrName.toLowerCase(), attribute.getKey());
        assertEquals("foo", attribute.getValue());
    }

    @Test
    public void handleLargeText() {
        StringBuilder sb = new StringBuilder(maxBufferLen);
        do {
            sb.append("A Large Amount of Text");
        } while (sb.length() < maxBufferLen);
        String text = sb.toString();
        String html = "<p>" + text + "</p>";

        DocumentModel doc = Jsoup.parse(html);
        Elements els = doc.select("p");
        assertEquals(1, els.size());
        ElementModel el = els.first();

        assertNotNull(el);
        assertEquals(text, el.text());
    }

    @Test
    public void handleLargeComment() {
        StringBuilder sb = new StringBuilder(maxBufferLen);
        do {
            sb.append("Quite a comment ");
        } while (sb.length() < maxBufferLen);
        String comment = sb.toString();
        String html = "<p><!-- " + comment + " --></p>";

        DocumentModel doc = Jsoup.parse(html);
        Elements els = doc.select("p");
        assertEquals(1, els.size());
        ElementModel el = els.first();

        assertNotNull(el);
        CommentModel child = (CommentModel) el.childNode(0);
        assertEquals(" " + comment + " ", child.getData());
    }

    @Test
    public void handleLargeCdata() {
        StringBuilder sb = new StringBuilder(maxBufferLen);
        do {
            sb.append("Quite a lot of CDATA <><><><>");
        } while (sb.length() < maxBufferLen);
        String cdata = sb.toString();
        String html = "<p><![CDATA[" + cdata + "]]></p>";

        DocumentModel doc = Jsoup.parse(html);
        Elements els = doc.select("p");
        assertEquals(1, els.size());
        ElementModel el = els.first();

        assertNotNull(el);
        TextNodeModel child = (TextNodeModel) el.childNode(0);
        assertEquals(cdata, el.text());
        assertEquals(cdata, child.getWholeText());
    }

    @Test
    public void handleLargeTitle() {
        StringBuilder sb = new StringBuilder(maxBufferLen);
        do {
            sb.append("Quite a long title");
        } while (sb.length() < maxBufferLen);
        String title = sb.toString();
        String html = "<title>" + title + "</title>";

        DocumentModel doc = Jsoup.parse(html);
        Elements els = doc.select("title");
        assertEquals(1, els.size());
        ElementModel el = els.first();

        assertNotNull(el);
        TextNodeModel child = (TextNodeModel) el.childNode(0);
        assertEquals(title, el.text());
        assertEquals(title, child.getWholeText());
        assertEquals(title, doc.title());
    }

    @Test
    public void cp1252Entities() {
        assertEquals("\u20ac", Jsoup.parse("&#0128;").text());
        assertEquals("\u201a", Jsoup.parse("&#0130;").text());
        assertEquals("\u20ac", Jsoup.parse("&#x80;").text());
    }

    @Test
    public void cp1252EntitiesProduceError() {
        Parser parser = new Parser(new HtmlTreeBuilder());
        parser.setTrackErrors(10);
        assertEquals("\u20ac", parser.parseInput("<html><body>&#0128;</body></html>", "").text());
        assertEquals(1, parser.getErrors().size());
    }

    @Test
    public void cp1252SubstitutionTable() throws UnsupportedEncodingException {
        for (int i = 0; i < Tokeniser.win1252Extensions.length; i++) {
            String s = new String(new byte[]{(byte) (i + Tokeniser.win1252ExtensionsStart)}, "Windows-1252");
            assertEquals(1, s.length());

            // some of these characters are illegal
            if (s.charAt(0) == '\ufffd') {
                continue;
            }

            assertEquals("At: " + i, s.charAt(0), Tokeniser.win1252Extensions[i]);
        }
    }
}
