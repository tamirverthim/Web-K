package org.xhtmlrenderer.script.html5.canvas.impl;

import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.xhtmlrenderer.script.html5.canvas.CanvasGradient;
import org.xhtmlrenderer.script.web_idl.DOMString;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taras Maslov
 * 7/23/2018
 */
public class CanvasGradientImpl implements CanvasGradient {
    
    private List<Pair<Double, String>> stops = new ArrayList<>();
    
    @Override
    public void addColorStop(double offset, @DOMString String color) {
        stops.add(new ImmutablePair<>(offset, color));
        prepare();
    }
    
    private LinearGradientPaint prepare(){
        val width = stops.stream().map(Pair::getKey).reduce(0d, (p1, p2) ->  p1 + p2).floatValue();
        val floatStops = new float[stops.size()];
        val colors = new Color[stops.size()];
        for (int i = 0; i < stops.size(); i++) {
            floatStops[i] = (float)stops.get(i).getKey().doubleValue();
            colors[i] = CanvasRenderingContext2DImpl.parseCSSColor(stops.get(i).getRight());
        }
        
        return new LinearGradientPaint(0, 0, width, 0, floatStops, colors);
    }
}
