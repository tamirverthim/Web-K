package org.xhtmlrenderer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.js.JS;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrowserContextImpl implements BrowserContext {

    XHTMLPanel panel;
    JS js;
    
    public BrowserContextImpl() {
    }

    @Override
    public XHTMLPanel getPanel() {
        return null;
    }

    @Override
    public JS getJS() {
        return null;
    }
    
}
