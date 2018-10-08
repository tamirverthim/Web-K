package com.earnix.kbrowser.simple.extend;

import com.earnix.kbrowser.dom.nodes.Element;
import com.earnix.kbrowser.extend.ReplacedElement;
import com.earnix.kbrowser.extend.ReplacedElementFactory;
import com.earnix.kbrowser.extend.UserAgentCallback;
import com.earnix.kbrowser.layout.LayoutContext;
import com.earnix.kbrowser.render.BlockBox;

public class NoReplacedElementFactory implements ReplacedElementFactory {

    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box,
                                                 UserAgentCallback uac, int cssWidth, int cssHeight) {
        return null;
    }

    public void remove(Element e) {

    }

    public void setFormSubmissionListener(FormSubmissionListener listener) {
        //TODO
    }

    public void reset() {
    }

}
