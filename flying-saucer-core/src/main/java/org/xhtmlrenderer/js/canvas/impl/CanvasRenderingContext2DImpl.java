package org.xhtmlrenderer.js.canvas.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.Element;
import org.xhtmlrenderer.js.canvas.*;
import org.xhtmlrenderer.js.canvas.HTMLCanvasElement;
import org.xhtmlrenderer.js.web_idl.Attribute;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class CanvasRenderingContext2DImpl implements CanvasRenderingContext2D {

    int width;
    int height;

    Object fillStyle;
    private Object strokeStyle;

    public CanvasRenderingContext2DImpl(int width, int height) {
        this.width = width;
        this.height = height;

        image = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleImage(width, height);
        g2d = (Graphics2D) image.getGraphics();
        g2d.setBackground(Color.red);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // initial state
        stateStack.push(new G2DState());
    }

    BufferedImage image;

    Graphics2D g2d;

    LinkedList<G2DState> stateStack = new LinkedList<>();

    private G2DState state() {
        return stateStack.getLast();
    }

    @Override
    public Attribute<HTMLCanvasElement> canvas() {
        return null;
    }

    @Override
    public void save() {
        stateStack.add(new G2DState());
    }

    @Override
    public void restore() {
        stateStack.pop();
        state().apply(g2d);
    }

    @Override
    public void scale(double x, double y) {
        state().scale(x, y);
        state().apply(g2d);
    }

    @Override
    public void rotate(double angle) {
        state().rotate(angle);
        state().apply(g2d);
    }

    @Override
    public void translate(double x, double y) {
        state().translate(x, y);
        state().apply(g2d);
    }

    @Override
    public void transform(double a, double b, double c, double d, double e, double f) {
        state().transform(a, b, c, d, e, f);
        state().apply(g2d);
    }

    @Override
    public void setTransform(double a, double b, double c, double d, double e, double f) {
        state().setTransform(a, b, c, d, e, f);
        state().apply(g2d);
    }

    @Override
    public Attribute<Double> globalAlpha() {
        return Attribute.readOnly(1.0);
    }

    @Override
    public Attribute<DOMString> globalCompositeOperation() {
        return null;
    }

    @Override
    public Attribute<Object> strokeStyle() {
        return Attribute.receive((value) -> {
            CanvasRenderingContext2DImpl.this.strokeStyle = value;
            if (value instanceof DOMString) {
                val color = Color.decode(value.toString());
                state().setStrokeColor(color);
                g2d.setBackground(color);
                g2d.setColor(color);
            } else {
                // todo grad and pattern
                throw new RuntimeException();
            }
        }).give(() -> fillStyle);
    }

    @Override
    public Attribute<Object> fillStyle() {
        return Attribute.receive((value) -> {
            CanvasRenderingContext2DImpl.this.fillStyle = value;
            if (value instanceof DOMString) {
                val color = Color.decode(value.toString());
                state().setFillColor(color);
                g2d.setBackground(color);
                g2d.setColor(color);
            } else {
                // todo grad and pattern
                throw new RuntimeException();
            }
        }).give(() -> fillStyle);
    }

    @Override
    public CanvasGradient createLinearGradient(double x0, double y0, double x1, double y1) {
        val res = new CanvasGradientImpl();
        // todo prepare
        return res;
    }

    @Override
    public CanvasGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1) {
        val res = new CanvasGradientImpl();
        // todo prepare
        return res;
    }

    @Override
    public CanvasPattern createPattern(CanvasImageSource image, DOMString repetition) {
        return null;
    }

    @Override
    public Attribute<Double> shadowOffsetX() {
        return null;
    }

    @Override
    public Attribute<Double> shadowOffsetY() {
        return null;
    }

    @Override
    public Attribute<Double> shadowBlur() {
        return null;
    }

    @Override
    public Attribute<DOMString> shadowColor() {
        return null;
    }

    @Override
    public void clearRect(double x, double y, double w, double h) {
        g2d.clearRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void fillRect(double x, double y, double w, double h) {
        state().apply(g2d, true);
        g2d.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void strokeRect(double x, double y, double w, double h) {
        state().apply(g2d, false);
        g2d.drawRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void beginPath() {
    }

    @Override
    public void fill() {
        state().apply(g2d, true);
        g2d.fill(path2D);
        path2D = new Path2D.Double();
    }

    @Override
    public void stroke() {
        state().apply(g2d, false);
        g2d.draw(path2D);
        path2D = new Path2D.Double();
    }

    @Override
    public void drawFocusIfNeeded(Element element) {

    }

    @Override
    public void clip() {
    }

    @Override
    public boolean isPointInPath(double x, double y) {
        return path2D.contains(x, y);
    }

    @Override
    public void fillText(DOMString text, double x, double y, double maxWidth) {
        state().apply(g2d, true);
        g2d.drawString(text.toString(), (int)x, (int)y);
        // todo handle max width
    }

    @Override
    public void strokeText(DOMString text, double x, double y, double maxWidth) {
        // todo implement with two fills, second - thin stroke
    }

    @Override
    public TextMetrics measureText(DOMString text) {
        return new TextMetricsImpl(g2d.getFontMetrics().stringWidth(text.toString()));
    }

    @Override
    public void drawImage(CanvasImageSource image, double dx, double dy) {

    }

    @Override
    public void drawImage(CanvasImageSource image, double dx, double dy, double dw, double dh) {

    }

    @Override
    public void drawImage(CanvasImageSource image, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh) {

    }

    @Override
    public void addHitRegion(HitRegionOptions options) {

    }

    @Override
    public void removeHitRegion(DOMString id) {

    }

    @Override
    public void clearHitRegions() {

    }

    @Override
    public ImageData createImageData(double sw, double sh) {
        return new ImageDataImpl(sw, sh, null);
    }

    @Override
    public ImageData createImageData(ImageData imagedata) {
        return imagedata;
    }

    @Override
    public ImageData getImageData(double sx, double sy, double sw, double sh) {
        return null;
    }

    @Override
    public void putImageData(ImageData imagedata, double dx, double dy) {
        val array = (Uint8ClampedArrayImpl) imagedata.data().get();
        try {
            // todo optimize
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(array.getBytes())), (int) dx, (int) dy, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putImageData(ImageData imagedata, double dx, double dy, double dirtyX, double dirtyY, double dirtyWidth, double dirtyHeight) {

    }


    public BufferedImage getBufferedImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // region path methods

    Path2D path2D = new Path2D.Double();

    @Override
    public void closePath() {
        path2D.closePath();
    }

    @Override
    public void moveTo(double x, double y) {
        path2D.moveTo(x, y);
    }

    @Override
    public void lineTo(double x, double y) {
        path2D.lineTo(x, y);
    }

    @Override
    public void quadraticCurveTo(double cpx, double cpy, double x, double y) {
        path2D.quadTo(cpx, cpy, x, y);
    }

    @Override
    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {
        path2D.curveTo(cp1x, cp1y, cp2x, cp2y, x, y);
    }

    @Override
    public void arcTo(double x1, double y1, double x2, double y2, double radius) {

    }

    @Override
    public void rect(double x, double y, double w, double h) {
        path2D.append(new Rectangle2D.Double(x, y, w, h), false);
    }

    @Override
    public void arc(double x, double y, double r, double startAngle, double endAngle, boolean counterclockwise) {
        double top = x - r;
        double left = y - r;
        double diam = 2*r;
        state().apply(g2d, false);
        
        path2D.append(
                new Arc2D.Double(top, left, diam, diam,
                        Math.toDegrees(counterclockwise ? - startAngle: endAngle),
                        Math.toDegrees(counterclockwise ? - endAngle: endAngle), Arc2D.OPEN), false);
    }

    @Override
    public Attribute<Double> lineWidth() {
        return Attribute.<Double>receive(value -> state().setLineWidth(value)).give(() -> state().getLineWidth());
    }

    @Override
    public Attribute<DOMString> lineCap() {
        return null;
    }

    @Override
    public Attribute<DOMString> lineJoin() {
        return null;
    }

    @Override
    public Attribute<Double> miterLimit() {
        return null;
    }

    // endregion
}
