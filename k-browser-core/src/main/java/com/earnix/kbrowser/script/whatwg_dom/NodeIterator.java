package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.Readonly;
import com.earnix.kbrowser.script.web_idl.SameObject;
import com.earnix.kbrowser.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface NodeIterator {
    @SameObject
    @Readonly
    Attribute<Node> root();

    @Readonly
    Attribute<Node> referenceNode();

    @Readonly
    Attribute<Boolean> pointerBeforeReferenceNode();

    @Readonly
    @Unsigned
    Attribute<Long> whatToShow();

    @Optional
    @Readonly
    Attribute<NodeFilter> filter();

    @Optional
    Node nextNode();

    @Optional
    Node previousNode();

    void detach();
}
