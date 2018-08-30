package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;

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
