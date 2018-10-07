package com.earnix.kbrowser.browser;

import com.earnix.kbrowser.dom.nodes.Element;
import com.earnix.kbrowser.simple.XHTMLPanel;

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
        Element document = panel.getRootBox().getElement();
        System.out.println(document.outerHtml());
    }
}
