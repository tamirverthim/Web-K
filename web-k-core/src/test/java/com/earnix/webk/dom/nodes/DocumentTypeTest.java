package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.parser.Parser;
import com.earnix.webk.script.whatwg_dom.impl.DocumentTypeImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the DocumentType node
 *
 * @author Jonathan Hedley, http://jonathanhedley.com/
 */
public class DocumentTypeTest {
    @Test
    public void constructorValidationOkWithBlankName() {
        DocumentTypeImpl fail = new DocumentTypeImpl("", "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorValidationThrowsExceptionOnNulls() {
        DocumentTypeImpl fail = new DocumentTypeImpl("html", null, null);
    }

    @Test
    public void constructorValidationOkWithBlankPublicAndSystemIds() {
        DocumentTypeImpl fail = new DocumentTypeImpl("html", "", "");
    }

    @Test
    public void outerHtmlGeneration() {
        DocumentTypeImpl html5 = new DocumentTypeImpl("html", "", "");
        assertEquals("<!doctype html>", html5.outerHtml());

        DocumentTypeImpl publicDocType = new DocumentTypeImpl("html", "-//IETF//DTD HTML//", "");
        assertEquals("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">", publicDocType.outerHtml());

        DocumentTypeImpl systemDocType = new DocumentTypeImpl("html", "", "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd");
        assertEquals("<!DOCTYPE html \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">", systemDocType.outerHtml());

        DocumentTypeImpl combo = new DocumentTypeImpl("notHtml", "--public", "--system");
        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", combo.outerHtml());
    }

    @Test
    public void testRoundTrip() {
        String base = "<!DOCTYPE html>";
        assertEquals("<!doctype html>", htmlOutput(base));
        assertEquals(base, xmlOutput(base));

        String publicDoc = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertEquals(publicDoc, htmlOutput(publicDoc));
        assertEquals(publicDoc, xmlOutput(publicDoc));

        String systemDoc = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertEquals(systemDoc, htmlOutput(systemDoc));
        assertEquals(systemDoc, xmlOutput(systemDoc));

        String legacyDoc = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertEquals(legacyDoc, htmlOutput(legacyDoc));
        assertEquals(legacyDoc, xmlOutput(legacyDoc));
    }

    private String htmlOutput(String in) {
        DocumentTypeImpl type = (DocumentTypeImpl) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }
}
