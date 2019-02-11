//package com.earnix.webk.runtime.whatwg_dom.impl;
//
//
//import com.earnix.webk.runtime.ScriptContext;
//import com.earnix.webk.runtime.html.canvas.impl.HTMLCanvasElementImpl;
//import com.earnix.webk.runtime.whatwg_dom.impl.ElementImpl;
//import com.earnix.webk.runtime.whatwg_dom.impl.NodeImpl;
//import com.earnix.webk.runtime.whatwg_dom.Element;
//import com.earnix.webk.runtime.whatwg_dom.Node;
//import lombok.experimental.var;
//
//import java.util.HashMap;
//
//
///**
// * Binds parser classes and JS DOM implementations
// *
// * @author Taras Maslov
// * 6/4/2018
// */
//public class ScriptDOMFactory {
//
//    private static HashMap<String, NodeCreator> elementsCreators = new HashMap<>();
//
//    private interface NodeCreator {
//        Node createNode(ScriptContext scriptContext, NodeImpl parsedNode);
//    }
//
//    static {
//        elementsCreators.put("canvas", (ctx, element) -> new HTMLCanvasElementImpl(
//                ctx, 
//                (ElementImpl) element
//        ));
//    }
//
//    public static Node get(ScriptContext scriptContext, NodeImpl key) {
//        if (key == null) {
//            return null;
//        }
//
//        var result = key.getScriptNode();
//        if (result == null) {
//            result = (NodeImpl) createScriptNode(scriptContext, key);
//            key.setScriptNode(result);
//        }
//        return result;
//    }
//
//    public static Element getElement(ScriptContext scriptContext, ElementImpl key) {
//        return (Element) get(scriptContext, key);
//    }
//
//    public static Node put(NodeImpl key, Node value) {
//        key.setScriptNode((NodeImpl) value);
//        return value;
//    }
//
//
////    public static Node createScriptNode(ScriptContext scriptContext, NodeImpl parsedNode) {
////        AssertHelper.assertNotNull(parsedNode);
////
////        Node result;
////
////        if (parsedNode instanceof CDataNodeModel) {
////            result = new CharacterDataImpl(scriptContext,  parsedNode);
////        } else if (parsedNode instanceof CommentModel) {
////            result = new CommentImpl(scriptContext, (CommentModel) parsedNode);
////        } else if (parsedNode instanceof DocumentTypeModel) {
////            result = new DocumentTypeImpl(scriptContext, (DocumentTypeModel) parsedNode);
////        } else if (parsedNode instanceof DocumentModel) {
////            result = new ElementImpl(scriptContext, (ElementModel) parsedNode);
////        } else if (parsedNode instanceof ElementModel) {
////            if (elementsCreators.containsKey(parsedNode.nodeName())) {
////                result = elementsCreators.get(parsedNode.nodeName()).createNode(scriptContext, parsedNode);
////            } else {
////                result = new ElementImpl(scriptContext, (ElementModel) parsedNode);
////            }
////        } else {
////            throw new RuntimeException();
////        }
////
////        return result;
////    }
//}
