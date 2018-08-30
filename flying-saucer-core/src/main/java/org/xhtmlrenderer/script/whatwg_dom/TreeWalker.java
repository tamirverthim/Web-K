package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
public interface TreeWalker {
    @SameObject @Readonly
    Attribute<Node> root();
    @Unsigned @Readonly Attribute<Long> whatToShow();
    @Optional @Readonly Attribute<NodeFilter> filter();
    Attribute<Node> currentNode();

    @Optional Node parentNode();
    @Optional Node firstChild();
    @Optional Node lastChild();
    @Optional Node previousSibling();
    @Optional Node nextSibling();
    @Optional Node previousNode();
    @Optional Node nextNode();
}
