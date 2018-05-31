package org.xhtmlrenderer.js.canvas.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class G2DState {
//    double sx = 1;
//    double sy = 1;
//    double rotation = 0;
//    double tx = 0;
//    double ty = 0;

    AffineTransform transform = new AffineTransform();

    void apply(Graphics2D graphics2D) {

    }

    public void scale(double x, double y) {
        transform.scale(x, y);
    }

    void rotate(double angle) {
        transform.rotate(angle);
    }

    void translate(double x, double y) {
        transform.translate(x, y);
    }

    void transform(double a, double b, double c, double d, double e, double f) {
        transform.concatenate(new AffineTransform(a, b, c, d, e, f));
    }

    void setTransform(double a, double b, double c, double d, double e, double f) {
        transform.setTransform(new AffineTransform(a, b, c, d, e, f));
    }
}
