package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.parser.Tag;

/**
 * Represents a {@link TextNode} as an {@link ElementModel}, to enable text nodes to be selected with
 * the {@link com.earnix.webk.dom.select.Selector} {@code :matchText} syntax.
 */
public class PseudoTextElement extends ElementModel {

    public PseudoTextElement(Tag tag, String baseUri, Attributes attributes) {
        super(tag, baseUri, attributes);
    }

    @Override
    void outerHtmlHead(Appendable accum, int depth, DocumentModel.OutputSettings out) {
    }

    @Override
    void outerHtmlTail(Appendable accum, int depth, DocumentModel.OutputSettings out) {
    }
}
