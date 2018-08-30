package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.html5.canvas.HTMLSlotElement;
import org.xhtmlrenderer.script.web_idl.Mixin;
import org.xhtmlrenderer.script.web_idl.ReadonlyAttribute;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Mixin
public interface Slotable {
    @Optional @ReadonlyAttribute
    HTMLSlotElement assignedSlot();
}
