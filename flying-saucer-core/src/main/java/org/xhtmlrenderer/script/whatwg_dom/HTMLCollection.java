package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
@Exposed(Window.class)
public interface HTMLCollection extends LegacyUnenumerableNamedProperties {
    @Unsigned @ReadonlyAttribute int length();
    @Getter @Optional Element item(@Unsigned int index);
    @Getter @Optional Element namedItem(@DOMString String name);
}

