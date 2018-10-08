package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.CEReactions;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.DefaultNull;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.HTMLConstructor;
import com.earnix.kbrowser.script.web_idl.Nullable;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.USVString;
import com.earnix.kbrowser.script.web_idl.Unsigned;
import com.earnix.kbrowser.script.whatwg_dom.Element;
import com.earnix.kbrowser.script.whatwg_dom.Window;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Exposed(Window.class)
@HTMLConstructor
public interface HTMLCanvasElement extends Element {

    @CEReactions
    @Unsigned
    Attribute<Integer> width();

    @CEReactions
    @Unsigned
    Attribute<Integer> height();

    @Nullable
    RenderingContext getContext(@DOMString String contextId, @Optional @DefaultNull Object options);

    @USVString
    String toDataURL(@Optional @DOMString String type, @Optional Object quality);

    void toBlob(BlobCallback _callback, @Optional @DOMString String type, @Optional Object quality);

    OffscreenCanvas transferControlToOffscreen();

}
