
package org.xhtmlrenderer.simple.extend.form;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

@Slf4j
public class DefaultSwingComponentFactory extends SwingComponentFactory
{
    @Override
    public JTextField createTextField(FormField field)
    {
        return new JTextField();
    }

    @Override
    public JButton createButton(FormField field)
    {
        return new JButton();
    }

    @Override
    public JTextArea createTextArea(FormField field, int rows, int cols)
    {
        JTextArea textArea = new JTextArea();
        if (rows > 0)
        {
            textArea.setRows(rows);
        }
        if (cols > 0)
        {
            textArea.setRows(cols);
        }
        return textArea;
    }

    @Override
    public JScrollPane createScrollPane(FormField field)
    {
        JScrollPane scrollPane = new JScrollPane();
        return scrollPane;
    }

    @Override
    public JCheckBox createCheckBox(FormField field)
    {
        return new JCheckBox();
    }

    @Override
    public JComboBox createComboBox(FormField field, List<NameValuePair> optionList)
    {
        ComboBoxModel model = new DefaultComboBoxModel(optionList.stream().map(kv -> kv.getName()).toArray());
        
        JComboBox combo = new JComboBox();
        combo.setModel(model);
        return combo;
    }

    @Override
    public JTable createMultipleOptionsList(FormField field, List<NameValuePair> optionList, int size)
    {
        TableModel tableModel = new DefaultTableModel(size, 1);
        int row = 0;
        for (NameValuePair nameValuePair : optionList) {
            tableModel.setValueAt(nameValuePair.getName(), row, 0); 
        }
        
        JTable table = new JTable(tableModel);
        table.setTableHeader(null);
        
        return table;
    }

    @Override
    public JRadioButton createRadioButton(FormField field)
    {
        return new JRadioButton();
    }

    @Override
    public FileInputComponent createFileInputComponent(FormField field)
    {
        return new FileInputComponent() {
            @Override
            public String getFilePath() {
                log.warn("getFilePath unimplemented");
                return null;
            }

            @Override
            public void setFilePath(String path) {
                log.warn("setFilePath unimplemented");
            }
        };
    }

}
