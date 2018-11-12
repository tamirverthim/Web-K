package com.earnix.webk.script;


import com.earnix.webk.dom.nodes.CDataNodeModel;
import com.earnix.webk.dom.nodes.CommentModel;
import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.dom.nodes.DocumentTypeModel;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;
import com.earnix.webk.script.html.canvas.impl.HTMLCanvasElementImpl;
import com.earnix.webk.script.impl.CharacterDataImpl;
import com.earnix.webk.script.impl.CommentImpl;
import com.earnix.webk.script.impl.DocumentTypeImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.NodeImpl;
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
        com.earnix.webk.script.whatwg_dom.Node createNode(NodeModel parsedNode, ScriptContext panel);
    }

    static {
        elementsCreators.put("canvas", (element, ctx) -> new HTMLCanvasElementImpl(
                (ElementModel) element,
                ctx
        ));
    }

    public static com.earnix.webk.script.whatwg_dom.Node get(NodeModel key, ScriptContext ctx) {
        if (key == null) {
            return null;
        }

        var result = key.getScriptNode();
        if (result == null) {
            result = (NodeImpl) createJSNode(key, ctx);
            key.setScriptNode(result);
        }
        return result;
    }

    public static com.earnix.webk.script.whatwg_dom.Element getElement(ElementModel key, ScriptContext ctx) {
        return (com.earnix.webk.script.whatwg_dom.Element) get(key, ctx);
    }

    public static com.earnix.webk.script.whatwg_dom.Node put(NodeModel key, com.earnix.webk.script.whatwg_dom.Node value) {
        key.setScriptNode((NodeImpl) value);
        return value;
    }


    public static com.earnix.webk.script.whatwg_dom.Node createJSNode(NodeModel parsedNode, ScriptContext ctx) {
        AssertHelper.assertNotNull(parsedNode);

        com.earnix.webk.script.whatwg_dom.Node result;

        if (parsedNode instanceof CDataNodeModel) {
            result = new CharacterDataImpl((CDataNodeModel) parsedNode, ctx);
        } else if (parsedNode instanceof CommentModel) {
            result = new CommentImpl((CommentModel) parsedNode, ctx);
        } else if (parsedNode instanceof DocumentTypeModel) {
            result = new DocumentTypeImpl((DocumentTypeModel) parsedNode, ctx);
        } else if (parsedNode instanceof DocumentModel) {
            result = new ElementImpl((ElementModel) parsedNode, ctx);
        } else if (parsedNode instanceof ElementModel) {
            if (elementsCreators.containsKey(parsedNode.nodeName())) {
                result = elementsCreators.get(parsedNode.nodeName()).createNode(parsedNode, ctx);
            } else {
                result = new ElementImpl((ElementModel) parsedNode, ctx);
            }
        } else {
            throw new RuntimeException();
        }

        return result;
    }
}
