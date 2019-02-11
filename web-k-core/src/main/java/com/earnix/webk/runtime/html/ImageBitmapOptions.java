package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.EnforceRange;
import com.earnix.webk.runtime.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Dictionary
public class ImageBitmapOptions {
    ImageOrientation imageOrientation = ImageOrientation.none;
    PremultiplyAlpha premultiplyAlpha = PremultiplyAlpha._default;
    ColorSpaceConversion colorSpaceConversion = ColorSpaceConversion._default;
    @EnforceRange @Unsigned int resizeWidth;
    @EnforceRange @Unsigned int resizeHeight;
    ResizeQuality resizeQuality = ResizeQuality.low;
}
