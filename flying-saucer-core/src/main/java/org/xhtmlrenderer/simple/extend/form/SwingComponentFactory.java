package org.xhtmlrenderer.simple.extend.form;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.util.List;
import java.util.Objects;

public abstract class SwingComponentFactory
{
	private static SwingComponentFactory instance;

	public static SwingComponentFactory getInstance()
	{
		return instance;
	}

	public static void setInstance(SwingComponentFactory f)
	{
		instance = Objects.requireNonNull(f);
	}

	public abstract JTextField createTextField(FormField field);

	public abstract JComboBox createComboBox(FormField field, List optionList);

	public abstract JButton createButton(FormField field);

}
