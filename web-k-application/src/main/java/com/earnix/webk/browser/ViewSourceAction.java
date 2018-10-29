package com.earnix.webk.browser;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.simple.XHTMLPanel;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * @author pwright
 */
public class ViewSourceAction extends AbstractAction {

    private final XHTMLPanel panel;

    ViewSourceAction(XHTMLPanel panel) {
        this.panel = panel;
    }

    public void actionPerformed(ActionEvent evt) {
        ElementModel document = panel.getRootBox().getElement();
        System.out.println(document.outerHtml());
    }
}
