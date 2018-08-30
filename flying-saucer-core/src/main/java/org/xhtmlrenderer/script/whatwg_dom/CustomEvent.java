package org.xhtmlrenderer.script.whatwg_dom;

import org.xhtmlrenderer.script.web_idl.Optional;
import org.xhtmlrenderer.script.future.Worker;
import org.xhtmlrenderer.script.web_idl.*;

/**
 * @author Taras Maslov
 * 6/19/2018
 */
//[Constructor(DOMString type, optional CustomEventInit eventInitDict),
//        Exposed=(Window,Worker)]
@Exposed({Window.class, Worker.class})    
public interface CustomEvent extends Event {
    
    void constructor(@DOMString String type, @Optional CustomEventInit eventInitDict);

    @Readonly
    Attribute<?> detail();

    void initCustomEvent(
            DOMString type,
            @Optional @DefaultBoolean(false) boolean bubbles,
            @Optional @DefaultBoolean(false) boolean cancelable, 
            @Optional Object detail
    );
}
