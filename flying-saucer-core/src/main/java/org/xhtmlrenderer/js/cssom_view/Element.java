package org.xhtmlrenderer.js.cssom_view;

import org.xhtmlrenderer.js.geom.DOMRect;
import org.xhtmlrenderer.js.geom.DOMRectList;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 8/13/2018
 */
@Partial
public interface Element {
    DOMRectList getClientRects();

    DOMRect getBoundingClientRect();

    void scrollIntoView(@Optional @OneOf({Boolean.class, ScrollIntoViewOptions.class}) Object arg);

    void scroll(@Optional ScrollToOptions options);

    void scroll(@Unrestricted double x, @Unrestricted double y);

    void scrollTo(@Optional ScrollToOptions options);

    void scrollTo(@Unrestricted double x, @Unrestricted double y);

    void scrollBy(@Optional ScrollToOptions options);

    void scrollBy(@Unrestricted double x, @Unrestricted double y);

    @Unrestricted
    Attribute<Double> scrollTop();

    @Unrestricted
    Attribute<Double> scrollLeft();

    @ReadonlyAttribute
    int scrollWidth();

    @ReadonlyAttribute
    int scrollHeight();

    @ReadonlyAttribute
    int clientTop();

    @ReadonlyAttribute
    int clientLeft();

    @ReadonlyAttribute
    int clientWidth();

    @ReadonlyAttribute
    int clientHeight();
}
