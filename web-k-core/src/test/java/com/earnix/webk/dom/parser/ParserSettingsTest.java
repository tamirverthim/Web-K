package com.earnix.webk.dom.parser;

import com.earnix.webk.dom.MultiLocaleRule;
import com.earnix.webk.dom.nodes.AttributesModel;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserSettingsTest {
    @Rule public MultiLocaleRule rule = new MultiLocaleRule();

    @Test
    @MultiLocaleRule.MultiLocaleTest
    public void caseSupport() {
        ParseSettings bothOn = new ParseSettings(true, true);
        ParseSettings bothOff = new ParseSettings(false, false);
        ParseSettings tagOn = new ParseSettings(true, false);
        ParseSettings attrOn = new ParseSettings(false, true);

        assertEquals("IMG", bothOn.normalizeTag("IMG"));
        assertEquals("ID", bothOn.normalizeAttribute("ID"));

        assertEquals("img", bothOff.normalizeTag("IMG"));
        assertEquals("id", bothOff.normalizeAttribute("ID"));

        assertEquals("IMG", tagOn.normalizeTag("IMG"));
        assertEquals("id", tagOn.normalizeAttribute("ID"));

        assertEquals("img", attrOn.normalizeTag("IMG"));
        assertEquals("ID", attrOn.normalizeAttribute("ID"));
    }

    @Test
    @MultiLocaleRule.MultiLocaleTest
    public void attributeCaseNormalization() throws Exception {
        ParseSettings parseSettings = new ParseSettings(false, false);

        String normalizedAttribute = parseSettings.normalizeAttribute("HIDDEN");

        assertEquals("hidden", normalizedAttribute);
    }

    @Test
    @MultiLocaleRule.MultiLocaleTest
    public void attributesCaseNormalization() throws Exception {
        ParseSettings parseSettings = new ParseSettings(false, false);
        AttributesModel attributes = new AttributesModel();
        attributes.put("ITEM", "1");

        AttributesModel normalizedAttributes = parseSettings.normalizeAttributes(attributes);

        assertEquals("item", normalizedAttributes.asList().get(0).getKey());
    }
}
