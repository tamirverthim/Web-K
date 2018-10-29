package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.parser.HtmlTreeBuilder;
import com.earnix.webk.dom.parser.Parser;

/**
 * Internal helpers for Nodes, to keep the actual node APIs relatively clean. A jsoup internal class, so don't use it as
 * there is no contract API).
 */
final class NodeUtils {
    /**
     * Get the output setting for this node,  or if this node has no document (or parent), retrieve the default output
     * settings
     */
    static DocumentModel.OutputSettings outputSettings(NodeModel node) {
        DocumentModel owner = node.ownerDocument();
        return owner != null ? owner.outputSettings() : (new DocumentModel("")).outputSettings();
    }

    /**
     * Get the parser that was used to make this node, or the default HTML parser if it has no parent.
     */
    static Parser parser(NodeModel node) {
        DocumentModel doc = node.ownerDocument();
        return doc != null && doc.parser() != null ? doc.parser() : new Parser(new HtmlTreeBuilder());
    }
}
