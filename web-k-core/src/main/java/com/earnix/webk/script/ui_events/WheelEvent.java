package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
public interface WheelEvent {
    // DeltaModeCode
    @Unsigned int DOM_DELTA_PIXEL = 0x00;
    @Unsigned int DOM_DELTA_LINE = 0x01;
    @Unsigned int DOM_DELTA_PAGE = 0x02;

    @ReadonlyAttribute
    double deltaX();

    @ReadonlyAttribute
    double deltaY();

    @ReadonlyAttribute
    double deltaZ();

    @ReadonlyAttribute
    @Unsigned
    int deltaMode();

    void constructor(@DOMString String type, @Optional WheelEventInit eventInitDict);
}
