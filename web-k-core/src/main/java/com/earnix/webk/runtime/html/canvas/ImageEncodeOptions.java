package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.DefaultString;
import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Dictionary
public class ImageEncodeOptions {
    @DefaultString("image/png") @DOMString String type;// = "image/png";
    @Unrestricted double quality = 1.0;
}
