package org.xhtmlrenderer.browser;

import org.xhtmlrenderer.dom.nodes.Document;
import org.xhtmlrenderer.script.ScriptContext;
import org.xhtmlrenderer.simple.XHTMLPanel;

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
