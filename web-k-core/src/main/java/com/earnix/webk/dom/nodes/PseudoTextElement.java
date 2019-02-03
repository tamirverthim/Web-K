package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.parser.Tag;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.whatwg_dom.impl.DocumentImpl;

/**
 * Represents a {@link com.earnix.webk.script.whatwg_dom.impl.TextImpl} as an {@link ElementImpl}, to enable text nodes to be selected with
 * the {@link com.earnix.webk.dom.select.Selector} {@code :matchText} syntax.
 */
public class PseudoTextElement extends ElementImpl {

    public PseudoTextElement(Tag tag, String baseUri, AttributesModel attributes) {
        super(tag, baseUri, attributes);
    }

    @Override
    protected void outerHtmlHead(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
    }

    @Override
    protected void outerHtmlTail(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
    }
}
