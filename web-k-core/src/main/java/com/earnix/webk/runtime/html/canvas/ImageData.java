package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.future.Worker;
import com.earnix.webk.runtime.web_idl.Constructor;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.ReadonlyAttribute;
import com.earnix.webk.runtime.web_idl.Serializable;
import com.earnix.webk.runtime.web_idl.Uint8ClampedArray;
import com.earnix.webk.runtime.web_idl.Unsigned;
import com.earnix.webk.runtime.dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Constructor
@Exposed({Window.class, Worker.class})
@Serializable
public interface ImageData {

    void constructor(@Unsigned long sw, @Unsigned long sh);

    void constructor(Uint8ClampedArray data, @Unsigned long sw, @Optional @Unsigned Long sh);

    @ReadonlyAttribute
    @Unsigned
    long width();

    @ReadonlyAttribute
    @Unsigned
    long height();

    @ReadonlyAttribute
    Uint8ClampedArray data();

}
