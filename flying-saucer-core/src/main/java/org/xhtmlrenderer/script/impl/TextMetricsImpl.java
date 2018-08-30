package org.xhtmlrenderer.script.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.xhtmlrenderer.script.html5.canvas.TextMetrics;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextMetricsImpl implements TextMetrics {
    int width;
    
    public TextMetricsImpl(int i) {
        width = i;
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double actualBoundingBoxLeft() {
        return 0;
    }

    @Override
    public double actualBoundingBoxRight() {
        return 0;
    }

    @Override
    public double fontBoundingBoxAscent() {
        return 0;
    }

    @Override
    public double fontBoundingBoxDescent() {
        return 0;
    }

    @Override
    public double actualBoundingBoxAscent() {
        return 0;
    }

    @Override
    public double actualBoundingBoxDescent() {
        return 0;
    }

    @Override
    public double emHeightAscent() {
        return 0;
    }

    @Override
    public double emHeightDescent() {
        return 0;
    }

    @Override
    public double hangingBaseline() {
        return 0;
    }

    @Override
    public double alphabeticBaseline() {
        return 0;
    }

    @Override
    public double ideographicBaseline() {
        return 0;
    }
}
