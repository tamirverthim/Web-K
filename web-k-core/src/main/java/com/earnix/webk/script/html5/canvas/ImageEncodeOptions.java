package com.earnix.webk.script.html5.canvas;

import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.DefaultString;
import com.earnix.webk.script.web_idl.Dictionary;
import com.earnix.webk.script.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Dictionary
public class ImageEncodeOptions {
    @DefaultString("image/png") @DOMString String type;// = "image/png";
    @Unrestricted double quality = 1.0;
}
