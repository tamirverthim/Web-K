package com.earnix.webk.browser.actions;

import com.earnix.webk.browser.WebKApplication;
import lombok.val;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

public class FontSizeAction extends AbstractAction {

    public static final int DECREMENT = 0;
    public static final int INCREMENT = 1;
    public static final int RESET = 2;

    private WebKApplication root;
    private int whichDirection;

    public FontSizeAction(WebKApplication root, int which) {
        super("FontSize");
        this.root = root;
        this.whichDirection = which;
    }

    public void actionPerformed(ActionEvent evt) {
        val panel = root.getPanel();
        switch (whichDirection) {
            case INCREMENT:
                panel.getView().incrementFontSize();
                break;
            case RESET:
                panel.getView().resetFontSize();
                break;
            case DECREMENT:
                panel.getView().decrementFontSize();
                break;
        }
    }
}
