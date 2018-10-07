package org.xhtmlrenderer.browser;

import org.xhtmlrenderer.dom.nodes.Element;
import org.xhtmlrenderer.simple.XHTMLPanel;

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
