package com.earnix.webk.swing;

import com.earnix.webk.simple.XHTMLPanel;
import lombok.val;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * @author Taras Maslov
 * 12/23/2018
 */
public class ContextMenu extends JPopupMenu {

    private final XHTMLPanel panel;

    public ContextMenu(XHTMLPanel panel) {
        this.panel = panel;

        val increaseFontItem = new JMenuItem("Increase font size");
        increaseFontItem.setBorder(BorderFactory.createEmptyBorder());
        increaseFontItem.addActionListener(e -> this.panel.incrementFontSize());
        add(increaseFontItem);


        val decreaseFontItem = new JMenuItem("Decrease font size");
        decreaseFontItem.addActionListener(e -> this.panel.decrementFontSize());
        add(decreaseFontItem);

        
        val resetFontSize = new JMenuItem("Reset font size");
        resetFontSize.addActionListener(e -> {
            this.panel.resetFontSize();
        });
        add(resetFontSize);
    }
}
