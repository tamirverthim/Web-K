package com.earnix.webk.browser;

import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 8/22/2018
 */
public interface BrowserContext {
    XHTMLPanel getView();

    DocumentModel getModel();

    ScriptContext getScriptContext();
}
