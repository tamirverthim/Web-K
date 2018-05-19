package org.xhtmlrenderer.simple.extend.form;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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

	public abstract JTextArea createTextArea(FormField field, int rows, int cols);

	public abstract JScrollPane createScrollPane(FormField field);

	public abstract JComboBox createComboBox(FormField field, List<NameValuePair> optionList);

	public abstract JTable createMultipleOptionsList(FormField field, List<NameValuePair> optionList, int size);

	public abstract JButton createButton(FormField field);

	public abstract JCheckBox createCheckBox(FormField field);

	public abstract JRadioButton createRadioButton(FormField field);
}
