package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.Readonly;
import com.earnix.webk.runtime.web_idl.SameObject;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface TreeWalker {
    @SameObject
    @Readonly
    Attribute<Node> root();

    @Unsigned
    @Readonly
    Attribute<Long> whatToShow();

    @Optional
    @Readonly
    Attribute<NodeFilter> filter();

    Attribute<Node> currentNode();

    @Optional
    Node parentNode();

    @Optional
    Node firstChild();

    @Optional
    Node lastChild();

    @Optional
    Node previousSibling();

    @Optional
    Node nextSibling();

    @Optional
    Node previousNode();

    @Optional
    Node nextNode();
}
