package org.xhtmlrenderer.js.whatwg_dom;

import org.xhtmlrenderer.js.web_idl.Optional;
import org.xhtmlrenderer.js.future.Worker;
import org.xhtmlrenderer.js.web_idl.*;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
//[Constructor(DOMString type, optional CustomEventInit eventInitDict),
//        Exposed=(Window,Worker)]
@Exposed({Window.class, Worker.class})    
public interface CustomEvent extends Event {
    
    void constructor(DOMString type, @Optional CustomEventInit eventInitDict);

    @Readonly
    Attribute<?> detail();

    void initCustomEvent(
            DOMString type,
            @Optional @DefaultBoolean(false) boolean bubbles,
            @Optional @DefaultBoolean(false) boolean cancelable, 
            @Optional Object detail
    );
}
