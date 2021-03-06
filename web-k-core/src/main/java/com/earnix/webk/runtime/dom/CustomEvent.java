package com.earnix.webk.runtime.dom;

import com.earnix.webk.runtime.future.Worker;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.DefaultBoolean;
import com.earnix.webk.runtime.web_idl.Exposed;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.Readonly;

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
