package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Exposed(Window.class)
public interface HTMLCollection {
    @Unsigned @ReadonlyAttribute long length();
    @Getter @Optional Element item(@Unsigned long index);
    @Getter @Optional Element namedItem(DOMString name);
}

