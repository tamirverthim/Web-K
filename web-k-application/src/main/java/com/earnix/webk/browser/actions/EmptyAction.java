package com.earnix.webk.browser.actions;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import java.awt.event.ActionEvent;

/**
 * @author Taras Maslov
 * 10/7/2018
 */
public class EmptyAction extends AbstractAction {
    public EmptyAction(String name, String shortDesc, Icon icon) {
        super(name, icon);
        putValue(Action.SHORT_DESCRIPTION, shortDesc);
    }

    public void actionPerformed(ActionEvent evt) {
    }
}
