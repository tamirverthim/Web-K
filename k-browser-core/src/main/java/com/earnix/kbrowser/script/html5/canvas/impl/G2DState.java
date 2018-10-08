package com.earnix.kbrowser.script.html5.canvas.impl;

import com.earnix.kbrowser.script.html5.canvas.CanvasLineCap;
import com.earnix.kbrowser.script.html5.canvas.CanvasLineJoin;
import com.earnix.kbrowser.script.html5.canvas.CanvasTextAlign;
import com.earnix.kbrowser.script.html5.canvas.CanvasTextBaseline;
import com.earnix.kbrowser.script.web_idl.impl.SequenceImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.var;
import lombok.val;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class G2DState implements Cloneable {

    AffineTransform transform = new AffineTransform();
    Color fillColor;
    Color strokeColor;
    double lineWidth = 1;
    int fontSize = 12;
    float globalAlpha = 1.0f;

    @Setter
    CanvasTextBaseline canvasTextBaseline;

    @Setter
    CanvasLineCap lineCap = CanvasLineCap.butt;

    @Setter
    CanvasLineJoin lineJoin = CanvasLineJoin.miter;

    @Setter
    double miterLimit = 10;

    @Setter
    SequenceImpl<Double> lineDash = new SequenceImpl<>();

    @Setter
    CanvasTextAlign textAlign = CanvasTextAlign.start;


    @Setter
    double lineDashOffset;

    void apply(Graphics2D graphics2D) {
        graphics2D.setTransform(transform);
        graphics2D.setStroke(createStroke());
        graphics2D.setFont(new Font("sans-serif", Font.PLAIN, fontSize)); // todo cache
    }


    private Stroke createStroke() {

        final int swingCap;
        switch (lineCap) {
            case butt:
                swingCap = BasicStroke.CAP_BUTT;
                break;
            case round:
                swingCap = BasicStroke.CAP_ROUND;
                break;
            case square:
                swingCap = BasicStroke.CAP_SQUARE;
                break;
            default:
                throw new IllegalArgumentException();
        }

        final int swingJoin;
        switch (lineJoin) {
            case miter:
                swingJoin = BasicStroke.JOIN_MITER;
                break;
            case bevel:
                swingJoin = BasicStroke.JOIN_BEVEL;
                break;
            case round:
                swingJoin = BasicStroke.JOIN_ROUND;
                break;
            default:
                throw new IllegalArgumentException();
        }

        float[] swingDash = new float[lineDash.length()];
        for (int i = 0; i < swingDash.length; i++) {
            swingDash[i] = lineDash.item(i).floatValue();
        }

        if (swingDash.length == 0) {
            return new BasicStroke((float) lineWidth, swingCap, swingJoin, (float) miterLimit);
        } else {
            return new BasicStroke((float) lineWidth, swingCap, swingJoin, (float) miterLimit, swingDash, (float) lineDashOffset);
        }
    }

    void apply(Graphics2D graphics2D, boolean fill) {
        apply(graphics2D);
        var color = fill ? fillColor : strokeColor;
        if (globalAlpha != 1.0) {
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), globalAlpha);
        }
        graphics2D.setColor(color);
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

    void setTransform(AffineTransform transform) {
        this.transform = transform;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public void setStrokeColor(Color color) {
        this.strokeColor = color;
    }

    public void setLineWidth(double value) {
        this.lineWidth = value;
    }

    public G2DState setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public G2DState setGlobalAlpha(float globalAlpha) {
        this.globalAlpha = globalAlpha;
        return this;
    }

    @Override
    public G2DState clone() {
        try {
            val res = (G2DState) super.clone();
            res.fillColor = fillColor;
            res.lineWidth = lineWidth;
            res.globalAlpha = globalAlpha;
            res.transform = new AffineTransform(this.transform);
            res.canvasTextBaseline = canvasTextBaseline;
            res.lineCap = lineCap;
            res.lineJoin = lineJoin;
            res.miterLimit = miterLimit;
            res.lineDash = lineDash;
            return res;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLineDashOffset(Double lineDashOffset) {
        this.lineDashOffset = lineDashOffset;
    }

    public Double getLineDashOffset() {
        return lineDashOffset;
    }

}
