package org.xhtmlrenderer.js.canvas.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.xhtmlrenderer.js.dom.DOMString;
import org.xhtmlrenderer.js.dom.Element;
import org.xhtmlrenderer.js.canvas.*;
import org.xhtmlrenderer.js.web_idl.Attribute;

import javax.imageio.ImageIO;
import java.awt.*;
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

    BufferedImage image;

    Graphics2D g2d;

    LinkedList<G2DState> stateStack;

    private G2DState state() {
        return stateStack.getLast();
    }

    ;

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
    }

    @Override
    public void rotate(double angle) {
        state().rotate(angle);
    }

    @Override
    public void translate(double x, double y) {
        state().translate(x, y);
    }

    @Override
    public void transform(double a, double b, double c, double d, double e, double f) {
        state().transform(a, b, c, d, e, f);
    }

    @Override
    public void setTransform(double a, double b, double c, double d, double e, double f) {
        state().setTransform(a, b, c, d, e, f);
    }

    @Override
    public Attribute<Double> globalAlpha() {
        return null;
    }

    @Override
    public Attribute<DOMString> globalCompositeOperation() {
        return null;
    }

    @Override
    public Attribute<Object> strokeStyle() {
        return null;
    }

    @Override
    public Attribute<Object> fillStyle() {
        return null;
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
        g2d.clearRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void strokeRect(double x, double y, double w, double h) {
        g2d.drawRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void beginPath() {
        log.trace("beginPath");
    }

    @Override
    public void fill() {
        g2d.fillRect(0, 0, getBufferedImage().getWidth(), getBufferedImage().getHeight());
    }

    @Override
    public void stroke() {
        log.trace("stroke");
    }

    @Override
    public void drawFocusIfNeeded(Element element) {

    }

    @Override
    public void clip() {
    }

    @Override
    public boolean isPointInPath(double x, double y) {
        return false;
    }

    @Override
    public void fillText(DOMString text, double x, double y, double maxWidth) {

    }

    @Override
    public void strokeText(DOMString text, double x, double y, double maxWidth) {

    }

    @Override
    public TextMetrics measureText(DOMString text) {
        return null;
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
}
