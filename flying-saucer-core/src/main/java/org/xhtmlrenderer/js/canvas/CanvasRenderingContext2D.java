package org.xhtmlrenderer.js.canvas;

import org.xhtmlrenderer.js.DOMString;
import org.xhtmlrenderer.js.Element;
import org.xhtmlrenderer.js.Optional;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.NullTreat;
import org.xhtmlrenderer.js.web_idl.OneOf;
import org.xhtmlrenderer.js.web_idl.TreatNullAs;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public interface CanvasRenderingContext2D {

    /** 
     * back-reference to the canvas 
     */
    @Attribute(readonly = true)
    HTMLCanvasElement canvas = null;

    // region - state -
    
    /** push state on state stack */
    void save(); 

    /** pop state stack and restore state */
    void restore(); 

    // endregion
    
    // region transformations (default: transform is the identity matrix)
    
    void scale(double x, double y);

    void rotate(double angle);

    void translate(double x, double y);

    void transform(double a, double b, double c, double d, double e, double f);

    void setTransform(double a, double b, double c, double d, double e, double f);

    // endregion
    
    
    
    // region compositing
    
    @Attribute(defaultInt = 1)
    double globalAlpha = 0; // (default: 1.0)
    
    @Attribute(defaultString = "source-over") 
    DOMString globalCompositeOperation = null;

    // endregion
    
    
    // region colors and styles (see also the CanvasDrawingStyles interface)
    
    @Attribute
    @OneOf({DOMString.class, CanvasGradient.class, CanvasPattern.class})
    Object strokeStyle = null; // (default: "black")
    
    @Attribute(defaultString = "black")
    @OneOf({DOMString.class, CanvasGradient.class, CanvasPattern.class})
    Object fillStyle = null;

    CanvasGradient createLinearGradient(double x0, double y0, double x1, double y1);

    CanvasGradient createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1);

    CanvasPattern createPattern(CanvasImageSource image, @TreatNullAs(NullTreat.EmptyString) DOMString repetition);

    
    
    // region shadows
    
    @Attribute(defaultInt = 0)
    double shadowOffsetX = 0; // (default: 0)

    @Attribute(defaultInt = 0)
    double shadowOffsetY = 0; // (default: 0)

    @Attribute(defaultInt = 0)
    double shadowBlur = 0; // (default: 0)

    @Attribute(defaultString = "transparent black")
    DOMString shadowColor = null; // (default: "transparent black")

    // endregion
    
    
    
    // region rects
    
    void clearRect(double x, double y, double w, double h);

    void fillRect(double x, double y, double w, double h);

    void strokeRect(double x, double y, double w, double h);
    
    // endregion
    
    

    //region path API (see also CanvasPathMethods)
    
    void beginPath();

    void fill();

    void stroke();

    void drawFocusIfNeeded(Element element);

    void clip();

    boolean isPointInPath(double x, double y);
    
    // endregion

    
    
    // region text (see also the CanvasDrawingStyles interface)
    
    void fillText(DOMString text, double x, double y, @Optional double maxWidth);

    void strokeText(DOMString text, double x, double y, @Optional double maxWidth);

    TextMetrics measureText(DOMString text);
    
    // endregion
    
    

    // drawing images
    void drawImage(CanvasImageSource image, double dx, double dy);

    void drawImage(CanvasImageSource image, double dx, double dy, double dw, double dh);

    void drawImage(CanvasImageSource image, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh);

    // hit regions
    void addHitRegion(HitRegionOptions options);

    void removeHitRegion(DOMString id);

    void clearHitRegions();

    // pixel manipulation
    ImageData createImageData(double sw, double sh);

    ImageData createImageData(ImageData imagedata);

    ImageData getImageData(double sx, double sy, double sw, double sh);

    void putImageData(ImageData imagedata, double dx, double dy);

    void putImageData(ImageData imagedata, double dx, double dy, double dirtyX, double dirtyY, double dirtyWidth, double dirtyHeight);

}
