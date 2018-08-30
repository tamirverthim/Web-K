package org.xhtmlrenderer.browser;

import org.xhtmlrenderer.dom.nodes.Document;
import org.xhtmlrenderer.script.ScriptContext;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 8/22/2018
 */
public interface BrowserContext {
    XHTMLPanel getView();
    Document getModel();
    ScriptContext getScriptContext();
}
