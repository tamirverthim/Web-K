package com.earnix.webk.browser;

import com.earnix.webk.runtime.whatwg_dom.impl.ElementImpl;
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
        ElementImpl document = panel.getRootBox().getElement();
        System.out.println(document.outerHtml());
    }
}
