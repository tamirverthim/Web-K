package org.xhtmlrenderer;

import org.jsoup.nodes.Document;
import org.xhtmlrenderer.js.JS;
import org.xhtmlrenderer.simple.XHTMLPanel;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
public interface BrowserContext {
    
    XHTMLPanel getPanel();
    
    JS getJS(); 
    
    String url();
    
    String documentUri();
    
    Document parsedDocument();
    
}
