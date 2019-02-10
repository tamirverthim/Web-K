package com.earnix.webk.simple.extend;

import com.earnix.webk.extend.ReplacedElement;
import com.earnix.webk.extend.ReplacedElementFactory;
import com.earnix.webk.extend.UserAgentCallback;
import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;
import com.earnix.webk.runtime.whatwg_dom.impl.ElementImpl;

public class NoReplacedElementFactory implements ReplacedElementFactory {

    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box,
                                                 UserAgentCallback uac, int cssWidth, int cssHeight) {
        return null;
    }

    public void remove(ElementImpl e) {

    }

    public void setFormSubmissionListener(FormSubmissionListener listener) {
        //TODO
    }

    public void reset() {
    }

}
