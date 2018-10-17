package com.earnix.webk.script.html.canvas.impl;

import com.earnix.webk.script.html.canvas.Path2D;
import com.earnix.webk.script.web_idl.DOMString;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 8/29/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Path2DImpl implements Path2D {


    @Override
    public void construct(@DOMString(oneOfIndex = 1) Object path) {

    }

    @Override
    public void closePath() {

    }

    @Override
    public void moveTo(double x, double y) {

    }

    @Override
    public void lineTo(double x, double y) {

    }

    @Override
    public void quadraticCurveTo(double cpx, double cpy, double x, double y) {

    }

    @Override
    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {

    }

    @Override
    public void arcTo(double x1, double y1, double x2, double y2, double radius) {

    }

    @Override
    public void rect(double x, double y, double w, double h) {

    }

    @Override
    public void arc(double x, double y, double radius, double startAngle, double endAngle, boolean anticlockwise) {

    }

    @Override
    public void ellipse(double x, double y, double radiusX, double radiusY, double rotation, double startAngle, double endAngle, boolean anticlockwise) {

    }
}
