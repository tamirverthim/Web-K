package org.xhtmlrenderer.js;


import lombok.experimental.var;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.xhtmlrenderer.js.impl.*;
import org.xhtmlrenderer.js.whatwg_dom.Node;

import java.util.HashMap;
import java.util.function.Function;

import static org.xhtmlrenderer.util.AssertHelper.assertNotNull;

/**
 * Binds parser classes and JS DOM implementations
 * 
 * @author Taras Maslov
 * 6/4/2018
 */
public class Binder {
    private static final HashMap<org.jsoup.nodes.Node, Node> registry = new HashMap<>();
    
    private static HashMap<String, Function<org.jsoup.nodes.Node, Node>> elementsCreators = new HashMap<>();
    
    static {
        elementsCreators.put("canvas", element -> new HTMLCanvasElementImpl(
                (org.jsoup.nodes.Element) element, 
                Integer.parseInt(element.attr("width")), 
                Integer.parseInt(element.attr("height"))
        ));
    }
    
    public static Node get(org.jsoup.nodes.Node key) {
        if(key == null){
            return null;
        }
        var result = registry.get(key);
        if(result == null){
            result = createJSNode(key);
            registry.put(key, result);
        } 
        return result;
    }

    public static Node put(org.jsoup.nodes.Node key, Node value) {
        return registry.put(key, value);
    }

    public static void remove(org.jsoup.nodes.Node key) {
        registry.remove(key);
    }
    
    public static Node createJSNode(org.jsoup.nodes.Node parsedNode){
        assertNotNull(parsedNode);
        
        Node result;
        
        if(parsedNode instanceof CDataNode){
            result = new CharacterDataImpl((CDataNode) parsedNode);
        } else if (parsedNode instanceof Comment) {
            result = new CommentImpl((Comment) parsedNode);
        } else if (parsedNode instanceof DocumentType) {
            result = new DocumentTypeImpl((DocumentType) parsedNode);
        } else if (parsedNode instanceof Document){
            throw new RuntimeException("Should be created on BrowserJs init");
        } else if (parsedNode instanceof org.jsoup.nodes.Element){
            if(elementsCreators.containsKey(parsedNode.nodeName())){
                result = elementsCreators.get(parsedNode.nodeName()).apply(parsedNode);
            } else {
                result = new ElementImpl((org.jsoup.nodes.Element) parsedNode);
            }
        } else {
            throw new RuntimeException();
        }
        
        return result;
    }
}
