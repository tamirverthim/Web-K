package com.earnix.webk.dom.select;

import com.earnix.webk.dom.helper.Validate;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.impl.NodeImpl;

/**
 * Depth-first node traversor. Use to iterate through all nodes under and including the specified root node.
 * <p>
 * This implementation does not use recursion, so a deep DOM does not risk blowing the stack.
 * </p>
 */
public class NodeTraversor {
    private NodeVisitor visitor;

    /**
     * Create a new traversor.
     *
     * @param visitor a class implementing the {@link NodeVisitor} interface, to be called when visiting each node.
     * @deprecated Just use the static {@link NodeTraversor#filter(NodeFilter, NodeModel)} method.
     */
    public NodeTraversor(NodeVisitor visitor) {
        this.visitor = visitor;
    }

    /**
     * Start a depth-first traverse of the root and all of its descendants.
     *
     * @param root the root node point to traverse.
     * @deprecated Just use the static {@link NodeTraversor#filter(NodeFilter, NodeModel)} method.
     */
    public void traverse(NodeImpl root) {
        traverse(visitor, root);
    }

    /**
     * Start a depth-first traverse of the root and all of its descendants.
     *
     * @param visitor Node visitor.
     * @param root    the root node point to traverse.
     */
    public static void traverse(NodeVisitor visitor, NodeImpl root) {
        NodeImpl node = root;
        int depth = 0;

        while (node != null) {
            visitor.head(node, depth);
            if (node.childNodeSize() > 0) {
                node = node.childNode(0);
                depth++;
            } else {
                while (node.nextSibling() == null && depth > 0) {
                    visitor.tail(node, depth);
                    node = node.parentNode();
                    depth--;
                }
                visitor.tail(node, depth);
                if (node == root)
                    break;
                node = node.nextSibling();
            }
        }
    }

    /**
     * Start a depth-first traverse of all elements.
     *
     * @param visitor  Node visitor.
     * @param elements Elements to filter.
     */
    public static void traverse(NodeVisitor visitor, Elements elements) {
        Validate.notNull(visitor);
        Validate.notNull(elements);
        for (ElementImpl el : elements)
            traverse(visitor, el);
    }

    /**
     * Start a depth-first filtering of the root and all of its descendants.
     *
     * @param filter Node visitor.
     * @param root   the root node point to traverse.
     * @return The filter result of the root node, or {@link NodeFilter.FilterResult#STOP}.
     */
    public static NodeFilter.FilterResult filter(NodeFilter filter, NodeImpl root) {
        NodeImpl node = root;
        int depth = 0;

        while (node != null) {
            NodeFilter.FilterResult result = filter.head(node, depth);
            if (result == NodeFilter.FilterResult.STOP)
                return result;
            // Descend into child nodes:
            if (result == NodeFilter.FilterResult.CONTINUE && node.childNodeSize() > 0) {
                node = node.childNode(0);
                ++depth;
                continue;
            }
            // No siblings, move upwards:
            while (node.nextSibling() == null && depth > 0) {
                // 'tail' current node:
                if (result == NodeFilter.FilterResult.CONTINUE || result == NodeFilter.FilterResult.SKIP_CHILDREN) {
                    result = filter.tail(node, depth);
                    if (result == NodeFilter.FilterResult.STOP)
                        return result;
                }
                NodeImpl prev = node; // In case we need to remove it below.
                node = node.parentNode();
                depth--;
                if (result == NodeFilter.FilterResult.REMOVE)
                    prev.remove(); // Remove AFTER finding parent.
                result = NodeFilter.FilterResult.CONTINUE; // Parent was not pruned.
            }
            // 'tail' current node, then proceed with siblings:
            if (result == NodeFilter.FilterResult.CONTINUE || result == NodeFilter.FilterResult.SKIP_CHILDREN) {
                result = filter.tail(node, depth);
                if (result == NodeFilter.FilterResult.STOP)
                    return result;
            }
            if (node == root)
                return result;
            NodeImpl prev = node; // In case we need to remove it below.
            node = node.nextSibling();
            if (result == NodeFilter.FilterResult.REMOVE)
                prev.remove(); // Remove AFTER finding sibling.
        }
        // root == null?
        return NodeFilter.FilterResult.CONTINUE;
    }

    /**
     * Start a depth-first filtering of all elements.
     *
     * @param filter   Node filter.
     * @param elements Elements to filter.
     */
    public static void filter(NodeFilter filter, Elements elements) {
        Validate.notNull(filter);
        Validate.notNull(elements);
        for (ElementImpl el : elements)
            if (filter(filter, el) == NodeFilter.FilterResult.STOP)
                break;
    }
}
