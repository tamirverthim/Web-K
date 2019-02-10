package com.earnix.webk.runtime.whatwg_dom.impl.nodes;

import com.earnix.webk.runtime.whatwg_dom.impl.parser.Tag;
import com.earnix.webk.runtime.whatwg_dom.impl.ElementImpl;
import com.earnix.webk.runtime.html.impl.DocumentImpl;

/**
 * Represents a {@link com.earnix.webk.runtime.whatwg_dom.impl.TextImpl} as an {@link ElementImpl}, to enable text nodes to be selected with
 * the {@link com.earnix.webk.runtime.whatwg_dom.impl.select.Selector} {@code :matchText} syntax.
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
