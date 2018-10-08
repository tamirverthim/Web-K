package com.earnix.kbrowser.script.cssom_view;

import com.earnix.kbrowser.script.geom.DOMRect;
import com.earnix.kbrowser.script.geom.DOMRectList;
import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.OneOf;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.Partial;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;
import com.earnix.kbrowser.script.web_idl.Unrestricted;

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
