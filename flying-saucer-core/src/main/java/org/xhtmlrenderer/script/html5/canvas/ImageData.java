package org.xhtmlrenderer.script.html5.canvas;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.future.Worker;
import org.xhtmlrenderer.script.web_idl.*;
import org.xhtmlrenderer.script.whatwg_dom.Window;

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

    @ReadonlyAttribute @Unsigned long width();
    @ReadonlyAttribute @Unsigned long height();
    @ReadonlyAttribute Uint8ClampedArray data();

}
