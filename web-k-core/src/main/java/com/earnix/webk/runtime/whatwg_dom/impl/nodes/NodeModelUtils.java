package com.earnix.webk.runtime.whatwg_dom.impl.nodes;

import com.earnix.webk.runtime.whatwg_dom.impl.parser.HtmlTreeBuilder;
import com.earnix.webk.runtime.whatwg_dom.impl.parser.Parser;
import com.earnix.webk.runtime.whatwg_dom.impl.NodeImpl;
import com.earnix.webk.runtime.html.impl.DocumentImpl;

/**
 * Internal helpers for Nodes, to keep the actual node APIs relatively clean. A jsoup internal class, so don't use it as
 * there is no contract API).
 */
public final class NodeModelUtils {
    /**
     * Get the output setting for this node,  or if this node has no document (or parent), retrieve the default output
     * settings
     */
    public static DocumentImpl.OutputSettings outputSettings(NodeImpl node) {
        DocumentImpl owner = node.ownerDocument();
        return owner != null ? owner.outputSettings() : new DocumentImpl("").outputSettings();
    }

    /**
     * Get the parser that was used to make this node, or the default HTML parser if it has no parent.
     */
    public static Parser parser(NodeImpl node) {
        DocumentImpl doc = node.ownerDocument();
        return doc != null && doc.parser() != null ? doc.parser() : new Parser(new HtmlTreeBuilder());
    }
}
