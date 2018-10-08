package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.html5.canvas.HTMLSlotElement;
import com.earnix.kbrowser.script.web_idl.Mixin;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;

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
