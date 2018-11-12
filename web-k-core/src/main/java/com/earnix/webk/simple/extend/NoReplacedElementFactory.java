package com.earnix.webk.simple.extend;

import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.extend.ReplacedElement;
import com.earnix.webk.extend.ReplacedElementFactory;
import com.earnix.webk.extend.UserAgentCallback;
import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;

public class NoReplacedElementFactory implements ReplacedElementFactory {

    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box,
                                                 UserAgentCallback uac, int cssWidth, int cssHeight) {
        return null;
    }

    public void remove(ElementModel e) {

    }

    public void setFormSubmissionListener(FormSubmissionListener listener) {
        //TODO
    }

    public void reset() {
    }

}
