package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.DefaultString;
import com.earnix.kbrowser.script.web_idl.Dictionary;
import com.earnix.kbrowser.script.web_idl.Unrestricted;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Dictionary
public class ImageEncodeOptions {
    @DefaultString("image/png") @DOMString String type;// = "image/png";
    @Unrestricted double quality = 1.0;
}
