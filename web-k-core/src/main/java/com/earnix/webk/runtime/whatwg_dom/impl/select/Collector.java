package com.earnix.webk.runtime.whatwg_dom.impl.select;

import com.earnix.webk.runtime.impl.ElementImpl;
import com.earnix.webk.runtime.impl.NodeImpl;

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
    public static Elements collect(Evaluator eval, ElementImpl root) {
        Elements elements = new Elements();
        NodeTraversor.traverse(new Accumulator(root, elements, eval), root);
        return elements;
    }

    private static class Accumulator implements NodeVisitor {
        private final ElementImpl root;
        private final Elements elements;
        private final Evaluator eval;

        Accumulator(ElementImpl root, Elements elements, Evaluator eval) {
            this.root = root;
            this.elements = elements;
            this.eval = eval;
        }

        public void head(NodeImpl node, int depth) {
            if (node instanceof ElementImpl) {
                ElementImpl el = (ElementImpl) node;
                if (eval.matches(root, el))
                    elements.add(el);
            }
        }

        public void tail(NodeImpl node, int depth) {
            // void
        }
    }

    public static ElementImpl findFirst(Evaluator eval, ElementImpl root) {
        FirstFinder finder = new FirstFinder(root, eval);
        NodeTraversor.filter(finder, root);
        return finder.match;
    }

    private static class FirstFinder implements NodeFilter {
        private final ElementImpl root;
        private ElementImpl match = null;
        private final Evaluator eval;

        FirstFinder(ElementImpl root, Evaluator eval) {
            this.root = root;
            this.eval = eval;
        }

        @Override
        public FilterResult head(NodeImpl node, int depth) {
            if (node instanceof ElementImpl) {
                ElementImpl el = (ElementImpl) node;
                if (eval.matches(root, el)) {
                    match = el;
                    return FilterResult.STOP;
                }
            }
            return FilterResult.CONTINUE;
        }

        @Override
        public FilterResult tail(NodeImpl node, int depth) {
            return FilterResult.CONTINUE;
        }
    }

}
