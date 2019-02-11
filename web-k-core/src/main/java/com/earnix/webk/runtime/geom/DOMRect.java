package com.earnix.webk.runtime.geom;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.Inherit;
import com.earnix.webk.runtime.web_idl.NewObject;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.Static;
import com.earnix.webk.runtime.web_idl.Unrestricted;


/**
 * @author Taras Maslov
 * 8/13/2018
 */
public interface DOMRect {
    
    @NewObject @Static
    DOMRect fromRect(@Optional DOMRectInit other);

    @Inherit
    @Unrestricted Attribute<Double> x();
    @Inherit @Unrestricted Attribute<Double> y();
    @Inherit @Unrestricted Attribute<Double>  width();
    @Inherit @Unrestricted Attribute<Double> height();
}
