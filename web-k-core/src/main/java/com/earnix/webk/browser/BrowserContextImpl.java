package com.earnix.webk.browser;

import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 8/22/2018
 */
public class BrowserContextImpl implements BrowserContext {
    @Override
    public XHTMLPanel getView() {
        return null;
    }

    @Override
    public DocumentModel getModel() {
        return null;
    }

    @Override
    public ScriptContext getScriptContext() {
        return null;
    }
}
