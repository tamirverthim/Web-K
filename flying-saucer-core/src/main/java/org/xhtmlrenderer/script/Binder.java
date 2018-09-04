package org.xhtmlrenderer.script;


import lombok.experimental.var;
import org.xhtmlrenderer.dom.nodes.*;
import org.xhtmlrenderer.script.html5.canvas.impl.HTMLCanvasElementImpl;
import org.xhtmlrenderer.script.impl.*;
import org.xhtmlrenderer.script.whatwg_dom.Element;
import org.xhtmlrenderer.script.whatwg_dom.Node;
import org.xhtmlrenderer.swing.BasicPanel;
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
//    private static final HashMap<org.xhtmlrenderer.dom.nodes.Node, Node> registry = new HashMap<>();
    
    private static HashMap<String, NodeCreator> elementsCreators = new HashMap<>();
    
    private interface NodeCreator {
        Node createNode(org.xhtmlrenderer.dom.nodes.Node parsedNode, BasicPanel panel);
    }
    
    static {
        elementsCreators.put("canvas", (element, panel) -> new HTMLCanvasElementImpl(
                (org.xhtmlrenderer.dom.nodes.Element) element, 
                GeneralUtil.parseInt(element.attr("width"), 300), 
                GeneralUtil.parseInt(element.attr("height"), 150),
                panel
        ));
    }
    
    public static Node get(org.xhtmlrenderer.dom.nodes.Node key, BasicPanel panel) {
        if(key == null){
            return null;
        }
        
        var result = key.getScriptNode();
        if(result == null){
            result = (NodeImpl) createJSNode(key, panel);
            key.setScriptNode(result);
        } 
        return result;
    }
    
    public static Element getElement(org.xhtmlrenderer.dom.nodes.Element key, BasicPanel panel){
        return (Element) get(key, panel);
    }

    public static Node put(org.xhtmlrenderer.dom.nodes.Node key, Node value) {
         key.setScriptNode((NodeImpl) value);
         return value;
    }

//    public static void remove(org.xhtmlrenderer.dom.nodes.Node key) {
//        registry.remove(key);
//    }
    
    public static Node createJSNode(org.xhtmlrenderer.dom.nodes.Node parsedNode, BasicPanel panel){
        assertNotNull(parsedNode);
        
        Node result;
        
        if(parsedNode instanceof CDataNode){
            result = new CharacterDataImpl((CDataNode) parsedNode, panel);
        } else if (parsedNode instanceof Comment) {
            result = new CommentImpl((Comment) parsedNode, panel);
        } else if (parsedNode instanceof DocumentType) {
            result = new DocumentTypeImpl((DocumentType) parsedNode, panel);
        } else if (parsedNode instanceof Document){
            result = new ElementImpl((org.xhtmlrenderer.dom.nodes.Element) parsedNode, panel);
        } else if (parsedNode instanceof org.xhtmlrenderer.dom.nodes.Element){
            if(elementsCreators.containsKey(parsedNode.nodeName())){
                result = elementsCreators.get(parsedNode.nodeName()).createNode(parsedNode, panel);
            } else {
                result = new ElementImpl((org.xhtmlrenderer.dom.nodes.Element) parsedNode, panel);
            }
        } else {
            throw new RuntimeException();
        }
        
        return result;
    }
}
