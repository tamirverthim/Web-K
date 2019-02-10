package com.earnix.webk.swing;

import com.earnix.webk.runtime.whatwg_dom.impl.ElementImpl;
import com.kitfox.svg.SVGCache;
import com.kitfox.svg.app.beans.SVGPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.awt.Dimension;
import java.io.StringReader;
import java.util.UUID;

/**
 * @author Taras Maslov
 * 8/30/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SVGReplacedElement extends SwingReplacedElement {

    ElementImpl model;
    int cssWidth;
    int cssHeight;

    public SVGReplacedElement(ElementImpl svgElement, int cssWidth, int cssHeight) {
        super(new SVGPanel());
        this.model = svgElement;
        this.cssWidth = cssWidth;
        this.cssHeight = cssHeight;
        initialize();
    }

    private void initialize() {
        val svgPanel = (SVGPanel) getJComponent();
        svgPanel.setAntiAlias(true);
        val svgCode = model.outerHtml();
        val name = UUID.randomUUID().toString();
        val uri = SVGCache.getSVGUniverse().loadSVG(new StringReader(svgCode), name, false);

        svgPanel.setSvgURI(uri);

        int width = svgPanel.getSVGWidth();
        int height = svgPanel.getSVGHeight();
        svgPanel.setScaleToFit(true);
        svgPanel.setPreferredSize(new Dimension(width, height));

        if (cssWidth > 0) width = cssWidth;

        if (cssHeight > 0) height = cssHeight;

        svgPanel.setSize(width, height);
        // Use an instanse of ourselves as the SAX event handler


    }

    @Override
    public boolean isRequiresInteractivePaint() {
        return true;
    }
}
