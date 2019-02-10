package com.earnix.webk.runtime.whatwg_dom.impl.nodes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeTest {
    @Test
    public void html() {
        AttributeModel attr = new AttributeModel("key", "value &");
        assertEquals("key=\"value &amp;\"", attr.html());
        assertEquals(attr.html(), attr.toString());
    }

    @Test
    public void testWithSupplementaryCharacterInAttributeKeyAndValue() {
        String s = new String(Character.toChars(135361));
        AttributeModel attr = new AttributeModel(s, "A" + s + "B");
        assertEquals(s + "=\"A" + s + "B\"", attr.html());
        assertEquals(attr.html(), attr.toString());
    }
}
