package com.earnix.webk.runtime.whatwg_dom;

import com.earnix.webk.runtime.html.canvas.HTMLSlotElement;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface Slotable {
    @Optional
    @ReadonlyAttribute
    HTMLSlotElement assignedSlot();
}
