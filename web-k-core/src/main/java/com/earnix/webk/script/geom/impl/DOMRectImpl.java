package com.earnix.webk.script.geom.impl;

import com.earnix.webk.script.geom.DOMRect;
import com.earnix.webk.script.geom.DOMRectInit;
import com.earnix.webk.script.web_idl.Attribute;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 12/24/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DOMRectImpl implements DOMRect {

    double x, y, width, height;

    @Override
    public DOMRect fromRect(DOMRectInit other) {
        return null;
    }

    @Override
    public Attribute<Double> x() {
        return new Attribute<Double>() {
            @Override
            public Double get() {
                return x;
            }

            @Override
            public void set(Double aDouble) {
                DOMRectImpl.this.x = aDouble;
            }
        };
    }

    @Override
    public Attribute<Double> y() {
        return new Attribute<Double>() {
            @Override
            public Double get() {
                return y;
            }

            @Override
            public void set(Double aDouble) {
                DOMRectImpl.this.y = aDouble;
            }
        };
    }

    @Override
    public Attribute<Double> width() {
        return new Attribute<Double>() {
            @Override
            public Double get() {
                return DOMRectImpl.this.width;
            }

            @Override
            public void set(Double aDouble) {
                DOMRectImpl.this.width = aDouble;
            }
        };
    }

    @Override
    public Attribute<Double> height() {
        return new Attribute<Double>() {
            @Override
            public Double get() {
                return DOMRectImpl.this.height;
            }

            @Override
            public void set(Double aDouble) {
                DOMRectImpl.this.height = aDouble;
            }
        };
    }
}
