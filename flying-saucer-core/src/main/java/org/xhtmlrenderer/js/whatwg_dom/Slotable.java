package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.html5.canvas.HTMLSlotElement;
import org.xhtmlrenderer.js.web_idl.Mixin;
import org.xhtmlrenderer.js.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface Slotable {
    @Optional @ReadonlyAttribute
    HTMLSlotElement assignedSlot();
}
