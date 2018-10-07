package com.earnix.kbrowser.browser;

import com.earnix.kbrowser.dom.nodes.Document;
import com.earnix.kbrowser.script.ScriptContext;
import com.earnix.kbrowser.simple.XHTMLPanel;

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
    public Document getModel() {
        return null;
    }

    @Override
    public ScriptContext getScriptContext() {
        return null;
    }
}
