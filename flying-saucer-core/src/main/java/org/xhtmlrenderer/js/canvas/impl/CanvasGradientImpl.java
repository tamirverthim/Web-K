package org.xhtmlrenderer.js.canvas.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.canvas.CanvasGradient;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CanvasGradientImpl implements CanvasGradient {
    
    GradientPaint gradientPaint;
    
    ArrayList<Pair<Double, DOMString>> spec = new ArrayList<>();
    
    @Override
    public void addColorStop(double offset, DOMString color) {
        spec.add(new ImmutablePair<>(offset, color));
    }
    
    void prepare(){
        // todo Paint creation
    }
}
