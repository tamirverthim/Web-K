package com.earnix.webk.script.cssom.impl;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.cssom.CSSStyleDeclaration;
import org.junit.Assert;
import org.junit.Test;

public class CSSStyleDeclarationImplTest
{
	@Test public void readElementStyle()
	{
		ElementModel model = new ElementModel("div");
		// write HTML style attribute
		model.attr("style", "background-color: #f00; display: none");

		CSSStyleDeclaration cssStyle = new CSSStyleDeclarationImpl(model, null);
		// read style from JS (style properties expected in CamelCase).
		String backgroundColor = cssStyle.getPropertyValue("backgroundColor");
		Assert.assertEquals("#f00", backgroundColor);

		String display = cssStyle.getPropertyValue("display");
		Assert.assertEquals("none", display);

	}

	@Test public void writeElementStyle()
	{

		ElementModel model = new ElementModel("div");
		// write HTML style attribute
		model.attr("style", "background-color: #f00; display: none");

		CSSStyleDeclaration cssStyle = new CSSStyleDeclarationImpl(model, null);

		// add css style from JS
		cssStyle.setProperty("fontSize", "22px", null);

		String fontSize = cssStyle.getPropertyValue("fontSize");
		Assert.assertEquals("22px", fontSize);

		// read style from HTML element
		String htmlElementStyle = model.attr("style");
		Assert.assertEquals(" \n" + " background-color: #f00; \n" + " display: none; \n" + " font-size: 22px;",
				htmlElementStyle);
	}


	@Test public void readElementComputedStyle()
	{
		CSSStyleDeclaration cssStyle = new CSSStyleDeclarationImpl("background-color: #f00; display: none", null);
		// read style from JS (style properties expected in CamelCase).
		String backgroundColor = cssStyle.getPropertyValue("backgroundColor");
		Assert.assertEquals("#f00", backgroundColor);

		String display = cssStyle.getPropertyValue("display");
		Assert.assertEquals("none", display);
	}
}