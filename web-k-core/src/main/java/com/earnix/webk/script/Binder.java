package com.earnix.webk.script;


import com.earnix.webk.dom.nodes.CDataNode;
import com.earnix.webk.dom.nodes.Comment;
import com.earnix.webk.dom.nodes.Document;
import com.earnix.webk.dom.nodes.DocumentType;
import com.earnix.webk.dom.nodes.Element;
import com.earnix.webk.dom.nodes.Node;
import com.earnix.webk.script.html5.canvas.impl.HTMLCanvasElementImpl;
import com.earnix.webk.script.impl.CharacterDataImpl;
import com.earnix.webk.script.impl.CommentImpl;
import com.earnix.webk.script.impl.DocumentTypeImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.swing.BasicPanel;
import com.earnix.webk.util.AssertHelper;
import lombok.experimental.var;

import java.util.HashMap;


/**
 * Binds parser classes and JS DOM implementations
 *
 * @author Taras Maslov
 * 6/4/2018
 */
public class Binder {

    private static HashMap<String, NodeCreator> elementsCreators = new HashMap<>();

    private interface NodeCreator {
        com.earnix.webk.script.whatwg_dom.Node createNode(Node parsedNode, BasicPanel panel);
    }

    static {
        elementsCreators.put("canvas", (element, panel) -> new HTMLCanvasElementImpl(
                (Element) element,
                panel
        ));
    }

    public static com.earnix.webk.script.whatwg_dom.Node get(Node key, BasicPanel panel) {
        if (key == null) {
            return null;
        }

        var result = key.getScriptNode();
        if (result == null) {
            result = (NodeImpl) createJSNode(key, panel);
            key.setScriptNode(result);
        }
        return result;
    }

    public static com.earnix.webk.script.whatwg_dom.Element getElement(Element key, BasicPanel panel) {
        return (com.earnix.webk.script.whatwg_dom.Element) get(key, panel);
    }

    public static com.earnix.webk.script.whatwg_dom.Node put(Node key, com.earnix.webk.script.whatwg_dom.Node value) {
        key.setScriptNode((NodeImpl) value);
        return value;
    }


    public static com.earnix.webk.script.whatwg_dom.Node createJSNode(Node parsedNode, BasicPanel panel) {
        AssertHelper.assertNotNull(parsedNode);

        com.earnix.webk.script.whatwg_dom.Node result;

        if (parsedNode instanceof CDataNode) {
            result = new CharacterDataImpl((CDataNode) parsedNode, panel);
        } else if (parsedNode instanceof Comment) {
            result = new CommentImpl((Comment) parsedNode, panel);
        } else if (parsedNode instanceof DocumentType) {
            result = new DocumentTypeImpl((DocumentType) parsedNode, panel);
        } else if (parsedNode instanceof Document) {
            result = new ElementImpl((Element) parsedNode, panel);
        } else if (parsedNode instanceof Element) {
            if (elementsCreators.containsKey(parsedNode.nodeName())) {
                result = elementsCreators.get(parsedNode.nodeName()).createNode(parsedNode, panel);
            } else {
                result = new ElementImpl((Element) parsedNode, panel);
            }
        } else {
            throw new RuntimeException();
        }

        return result;
    }
}
