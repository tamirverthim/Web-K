package com.earnix.webk.script.html.canvas;

import com.earnix.webk.script.future.Worker;
import com.earnix.webk.script.web_idl.Constructor;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Serializable;
import com.earnix.webk.script.web_idl.Uint8ClampedArray;
import com.earnix.webk.script.web_idl.Unsigned;
import com.earnix.webk.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Constructor(true)
@Exposed({Window.class, Worker.class})
@Serializable
public interface ImageData {

    void construct(@Unsigned long sw, @Unsigned long sh);

    void construct(Uint8ClampedArray data, @Unsigned long sw, @Optional @Unsigned Long sh);

    @ReadonlyAttribute
    @Unsigned
    long width();

    @ReadonlyAttribute
    @Unsigned
    long height();

    @ReadonlyAttribute
    Uint8ClampedArray data();

}
