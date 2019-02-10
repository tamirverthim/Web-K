package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface NavigatorID {
    @ReadonlyAttribute
    @DOMString String appCodeName(); // constant "Mozilla"

    @ReadonlyAttribute
    @DOMString String appName(); // constant "Netscape"

    @ReadonlyAttribute
    @DOMString String appVersion();

    @ReadonlyAttribute
    @DOMString String platform();

    @ReadonlyAttribute
    @DOMString String product(); // constant "Gecko"

    @Exposed(Window.class)
    @ReadonlyAttribute
    @DOMString String productSub();

    @ReadonlyAttribute
    @DOMString String userAgent();

    @Exposed(Window.class)
    @ReadonlyAttribute
    @DOMString String vendor();

    @Exposed(Window.class)
    @ReadonlyAttribute
    @DOMString String vendorSub(); // constant ""
}
