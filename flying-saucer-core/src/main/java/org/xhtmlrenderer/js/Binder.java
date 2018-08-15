package org.xhtmlrenderer.js;


import lombok.experimental.var;
import org.jsoup.nodes.*;
import org.xhtmlrenderer.js.html5.canvas.impl.HTMLCanvasElementImpl;
import org.xhtmlrenderer.js.impl.*;
import org.xhtmlrenderer.js.whatwg_dom.Element;
import org.xhtmlrenderer.js.whatwg_dom.Node;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.util.GeneralUtil;

import java.util.HashMap;

import static org.xhtmlrenderer.util.AssertHelper.assertNotNull;

/**
 * Binds parser classes and JS DOM implementations
 * 
 * @author Taras Maslov
 * 6/4/2018
 */
public class Binder {
    private static final HashMap<org.jsoup.nodes.Node, Node> registry = new HashMap<>();
    
    private static HashMap<String, NodeCreator> elementsCreators = new HashMap<>();
    
    private interface NodeCreator {
        Node createNode(org.jsoup.nodes.Node parsedNode, XHTMLPanel panel);
    }
    
    static {
        elementsCreators.put("canvas", (element, panel) -> new HTMLCanvasElementImpl(
                (org.jsoup.nodes.Element) element, 
                GeneralUtil.parseInt(element.attr("width"), 300), 
                GeneralUtil.parseInt(element.attr("height"), 150),
                panel
        ));
    }
    
    public static Node get(org.jsoup.nodes.Node key, XHTMLPanel panel) {
        if(key == null){
            return null;
        }
        var result = registry.get(key);
        if(result == null){
            result = createJSNode(key, panel);
            registry.put(key, result);
        } 
        return result;
    }
    
    public static Element getElement(org.jsoup.nodes.Element key, XHTMLPanel panel){
        return (Element) get(key, panel);
    }

    public static Node put(org.jsoup.nodes.Node key, Node value) {
        return registry.put(key, value);
    }

    public static void remove(org.jsoup.nodes.Node key) {
        registry.remove(key);
    }
    
    public static Node createJSNode(org.jsoup.nodes.Node parsedNode, XHTMLPanel panel){
        assertNotNull(parsedNode);
        
        Node result;
        
        if(parsedNode instanceof CDataNode){
            result = new CharacterDataImpl((CDataNode) parsedNode, panel);
        } else if (parsedNode instanceof Comment) {
            result = new CommentImpl((Comment) parsedNode, panel);
        } else if (parsedNode instanceof DocumentType) {
            result = new DocumentTypeImpl((DocumentType) parsedNode, panel);
        } else if (parsedNode instanceof Document){
            throw new RuntimeException("Should be created on BrowserJs init");
        } else if (parsedNode instanceof org.jsoup.nodes.Element){
            if(elementsCreators.containsKey(parsedNode.nodeName())){
                result = elementsCreators.get(parsedNode.nodeName()).createNode(parsedNode, panel);
            } else {
                result = new ElementImpl((org.jsoup.nodes.Element) parsedNode, panel);
            }
        } else {
            throw new RuntimeException();
        }
        
        return result;
    }
}
