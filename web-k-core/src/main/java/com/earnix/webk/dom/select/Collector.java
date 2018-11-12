package com.earnix.webk.dom.select;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;

/**
 * Collects a list of elements that match the supplied criteria.
 *
 * @author Jonathan Hedley
 */
public class Collector {

    private Collector() {
    }

    /**
     * Build a list of elements, by visiting root and every descendant of root, and testing it against the evaluator.
     *
     * @param eval Evaluator to test elements against
     * @param root root of tree to descend
     * @return list of matches; empty if none
     */
    public static Elements collect(Evaluator eval, ElementModel root) {
        Elements elements = new Elements();
        NodeTraversor.traverse(new Accumulator(root, elements, eval), root);
        return elements;
    }

    private static class Accumulator implements NodeVisitor {
        private final ElementModel root;
        private final Elements elements;
        private final Evaluator eval;

        Accumulator(ElementModel root, Elements elements, Evaluator eval) {
            this.root = root;
            this.elements = elements;
            this.eval = eval;
        }

        public void head(NodeModel node, int depth) {
            if (node instanceof ElementModel) {
                ElementModel el = (ElementModel) node;
                if (eval.matches(root, el))
                    elements.add(el);
            }
        }

        public void tail(NodeModel node, int depth) {
            // void
        }
    }

    public static ElementModel findFirst(Evaluator eval, ElementModel root) {
        FirstFinder finder = new FirstFinder(root, eval);
        NodeTraversor.filter(finder, root);
        return finder.match;
    }

    private static class FirstFinder implements NodeFilter {
        private final ElementModel root;
        private ElementModel match = null;
        private final Evaluator eval;

        FirstFinder(ElementModel root, Evaluator eval) {
            this.root = root;
            this.eval = eval;
        }

        @Override
        public FilterResult head(NodeModel node, int depth) {
            if (node instanceof ElementModel) {
                ElementModel el = (ElementModel) node;
                if (eval.matches(root, el)) {
                    match = el;
                    return FilterResult.STOP;
                }
            }
            return FilterResult.CONTINUE;
        }

        @Override
        public FilterResult tail(NodeModel node, int depth) {
            return FilterResult.CONTINUE;
        }
    }

}
