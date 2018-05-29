package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.DOMString;
import org.xhtmlrenderer.js.Element;
import org.xhtmlrenderer.js.Optional;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface CanvasRenderingContext2D {

    // back-reference to the canvas
    // readonly attribute 
    HTMLCanvasElement canvas = null;

    // state
    abstract void save(); // push state on state stack
    abstract void restore(); // pop state stack and restore state

    // transformations (default: transform is the identity matrix)
    abstract void scale( double x,  double y);
    abstract void rotate( double angle);
    abstract void translate( double x,  double y);
    abstract void transform( double a,  double b,  double c,  double d,  double e,  double f);
    abstract void setTransform( double a,  double b,  double c,  double d,  double e,  double f);

    // compositing
    //attribute  
    double globalAlpha = 0; // (default: 1.0)
    //attribute 
    DOMString globalCompositeOperation = null; // (default: "source-over")

    // colors and styles (see also the CanvasDrawingStyles interface)
   // attribute 
    //(DOMString or CanvasGradient or CanvasPattern) 
    Object strokeStyle = null; // (default: "black")
    //attribute (DOMString or CanvasGradient or CanvasPattern) 
    Object fillStyle = null; // (default: "black")

    CanvasGradient createLinearGradient(double x0, double y0, double x1, double y1);
    CanvasGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1);
    CanvasPattern createPattern(CanvasImageSource image, @TreatNullAs("EmptyString") DOMString repetition);

    // shadows
    // attribute  
    double shadowOffsetX = 0; // (default: 0)
    // attribute 
    double shadowOffsetY = 0; // (default: 0)
    //attribute  
    double shadowBlur = 0; // (default: 0)
    //attribute 
    DOMString shadowColor = null; // (default: "transparent black")

    // rects
    void clearRect( double x,  double y,  double w,  double h);
    void fillRect( double x,  double y,  double w,  double h);
    void strokeRect( double x,  double y,  double w,  double h);

    // path API (see also CanvasPathMethods)
    void beginPath();
    void fill();
    void stroke();
    void drawFocusIfNeeded(Element element);
    void clip();
    boolean isPointInPath( double x,  double y);

    // text (see also the CanvasDrawingStyles interface)
    void fillText(DOMString text,  double x,  double y, @Optional double maxWidth);
    void strokeText(DOMString text,  double x,  double y, @Optional  double maxWidth);
    TextMetrics measureText(DOMString text);

    // drawing images
    void drawImage(CanvasImageSource image,  double dx,  double dy);
    void drawImage(CanvasImageSource image,  double dx,  double dy,  double dw,  double dh);
    void drawImage(CanvasImageSource image,  double sx,  double sy,  double sw,  double sh,  double dx,  double dy,  double dw,  double dh);

    // hit regions
    void addHitRegion(HitRegionOptions options);
    void removeHitRegion(DOMString id);
    void clearHitRegions();

    // pixel manipulation
    ImageData createImageData( double sw,  double sh);
    ImageData createImageData(ImageData imagedata);
    ImageData getImageData(double sx, double sy, double sw, double sh);
    void putImageData(ImageData imagedata, double dx, double dy);
    void putImageData(ImageData imagedata, double dx, double dy, double dirtyX, double dirtyY, double dirtyWidth, double dirtyHeight);
    
}
