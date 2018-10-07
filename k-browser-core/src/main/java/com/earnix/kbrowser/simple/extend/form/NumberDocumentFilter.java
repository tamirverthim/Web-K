package com.earnix.kbrowser.simple.extend.form;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * @author Taras Maslov
 * 9/24/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NumberDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (!isValid(string)) {
            return;
        }
        super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (!isValid(text)) {
            return;
        }
        super.replace(fb, offset, length, text, attrs);
    }

    private boolean isValid(String string) {
        return !string.chars()
                .filter(c -> !Character.isDigit(c) && c != '.')
                .findAny()
                .isPresent();
    }
}
