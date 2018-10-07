package com.earnix.kbrowser.tools;

import com.earnix.kbrowser.extend.ReplacedElement;
import com.earnix.kbrowser.extend.UserAgentCallback;
import com.earnix.kbrowser.layout.LayoutContext;
import com.earnix.kbrowser.render.BlockBox;

/**
 * @author patrick
 */
public abstract class ElementReplacer {
    public abstract boolean isElementNameMatch();

    public abstract String getElementNameMatch();

    public abstract boolean accept(LayoutContext context, com.earnix.kbrowser.dom.nodes.Element element);

    public abstract ReplacedElement replace(final LayoutContext context,
                                            final BlockBox box,
                                            final UserAgentCallback uac,
                                            final int cssWidth,
                                            final int cssHeight
    );

    public abstract void clear(final com.earnix.kbrowser.dom.nodes.Element element);

    public abstract void reset();
}
