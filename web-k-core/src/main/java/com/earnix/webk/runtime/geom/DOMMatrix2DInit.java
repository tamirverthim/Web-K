package com.earnix.webk.runtime.geom;

import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.Unrestricted;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Dictionary
@FieldDefaults(level = AccessLevel.PUBLIC)
public class DOMMatrix2DInit {
    @Unrestricted double a;
    @Unrestricted double b;
    @Unrestricted double c;
    @Unrestricted double d;
    @Unrestricted double e;
    @Unrestricted double f;
    @Unrestricted double m11;
    @Unrestricted double m12;
    @Unrestricted double m21;
    @Unrestricted double m22;
    @Unrestricted double m41;
    @Unrestricted double m42;
}
