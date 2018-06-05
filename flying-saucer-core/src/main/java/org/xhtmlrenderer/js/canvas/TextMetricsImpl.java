package org.xhtmlrenderer.js.canvas;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.xhtmlrenderer.js.web_idl.Attribute;

/**
 * @author Taras Maslov
 * 6/5/2018
 */
@AllArgsConstructor
@ToString(of = "width")
public class TextMetricsImpl implements TextMetrics {
    
    private double width;
    
    @Override
    public Attribute<Double> width() {
        return Attribute.readOnly(width);
    }

    
}
