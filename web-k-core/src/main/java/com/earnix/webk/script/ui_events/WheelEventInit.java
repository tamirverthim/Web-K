package com.earnix.webk.script.ui_events;

import com.earnix.webk.script.web_idl.Dictionary;
import com.earnix.webk.script.web_idl.Unsigned;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 10/30/2018
 */
@Dictionary
@FieldDefaults(level = AccessLevel.PUBLIC)
public class WheelEventInit extends MouseEventInit {
    double deltaX = 0.0;
    double deltaY = 0.0;
    double deltaZ = 0.0;
    @Unsigned int deltaMode = 0;
}
