package com.earnix.webk.simple.extend.form;

import lombok.extern.slf4j.Slf4j;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

@Slf4j
public class DefaultSwingComponentFactory extends SwingComponentFactory {
    @Override
    public JTextField createTextField(TextField formTextField) {
        return new JTextField();
    }

    @Override
    public JButton createButton(FormField field) {
        return new JButton();
    }

    @Override
    public JTextArea createTextArea(FormField field, int rows, int cols) {
        JTextArea textArea = new JTextArea();
        if (rows > 0) {
            textArea.setRows(rows);
        }
        if (cols > 0) {
            textArea.setRows(cols);
        }
        return textArea;
    }

    @Override
    public JScrollPane createScrollPane(FormField field) {
        return new JScrollPane();
    }

    @Override
    public JCheckBox createCheckBox(FormField field) {
        return new JCheckBox();
    }

    @Override
    public JComboBox createComboBox(FormField field, List<NameValuePair> optionList) {
        ComboBoxModel model = new DefaultComboBoxModel(optionList.stream().map(kv -> kv.getName()).toArray());

        JComboBox combo = new JComboBox();
        combo.setModel(model);
        return combo;
    }

    @Override
    public JTable createMultipleOptionsList(FormField field, List<NameValuePair> optionList, int size) {
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
    public JRadioButton createRadioButton(FormField field) {
        return new JRadioButton();
    }

    @Override
    public FileInputComponent createFileInputComponent(FormField field) {
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

    @Override
    public void showErrorDialog(String message, JComponent parent) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.WARNING_MESSAGE);
    }
}
