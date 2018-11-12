package com.earnix.webk.script.html;

import com.earnix.webk.script.future.Worker;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Serializable;
import com.earnix.webk.script.web_idl.Transferable;
import com.earnix.webk.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Serializable
@Transferable
@Exposed({Window.class, Worker.class})
public interface ImageBitmap {
    
    @ReadonlyAttribute
    @Unsigned
    int width();

    @ReadonlyAttribute
    @Unsigned
    int height();

    void close();
    
}
