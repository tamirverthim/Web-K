package com.earnix.webk.runtime.dom.impl;

import com.earnix.webk.runtime.dom.CDATASection;

import java.io.IOException;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
public class CDATASectionImpl extends TextImpl implements CDATASection {

    // region model
    
    public CDATASectionImpl(String text) {
        super(text);
    }

    @Override
    public String nodeName() {
        return "#cdata";
    }

    /**
     * Get the unencoded, <b>non-normalized</b> text content of this CDataNode.
     *
     * @return unencoded, non-normalized text
     */
    @Override
    public String text() {
        return getWholeText();
    }

    @Override
    protected void outerHtmlHead(Appendable accum, int depth, DocumentImpl.OutputSettings out) throws IOException {
        accum
                .append("<![CDATA[")
                .append(getWholeText());
    }

    @Override
    protected void outerHtmlTail(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
        try {
            accum.append("]]>");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    // endregion
}