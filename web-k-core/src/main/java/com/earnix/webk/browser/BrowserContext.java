package com.earnix.webk.browser;

import com.earnix.webk.dom.nodes.Document;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 8/22/2018
 */
public interface BrowserContext {
    XHTMLPanel getView();

    Document getModel();

    ScriptContext getScriptContext();
}
