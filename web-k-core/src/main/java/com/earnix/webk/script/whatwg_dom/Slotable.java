package com.earnix.webk.script.whatwg_dom;

import com.earnix.webk.script.html.canvas.HTMLSlotElement;
import com.earnix.webk.script.web_idl.Mixin;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;

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
