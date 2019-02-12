package com.earnix.webk.tools;

import com.earnix.webk.extend.ReplacedElement;
import com.earnix.webk.extend.UserAgentCallback;
import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;
import com.earnix.webk.runtime.dom.impl.ElementImpl;

/**
 * @author patrick
 */
public abstract class ElementReplacer {
    public abstract boolean isElementNameMatch();

    public abstract String getElementNameMatch();

    public abstract boolean accept(LayoutContext context, ElementImpl element);

    public abstract ReplacedElement replace(final LayoutContext context,
                                            final BlockBox box,
                                            final UserAgentCallback uac,
                                            final int cssWidth,
                                            final int cssHeight
    );

    public abstract void clear(final ElementImpl element);

    public abstract void reset();
}
