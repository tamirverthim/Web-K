package com.earnix.webk.script.geom.impl;

import com.earnix.webk.script.geom.DOMRect;
import com.earnix.webk.script.geom.DOMRectInit;
import com.earnix.webk.script.web_idl.Attribute;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 12/24/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DOMRectImpl implements DOMRect {

    final double x, y, width, height;

    @Override
    public DOMRect fromRect(DOMRectInit other) {
        return null;
    }

    @Override
    public Attribute<Double> x() {
        return null;
    }

    @Override
    public Attribute<Double> y() {
        return null;
    }

    @Override
    public Attribute<Double> width() {
        return null;
    }

    @Override
    public Attribute<Double> height() {
        return null;
    }
}
