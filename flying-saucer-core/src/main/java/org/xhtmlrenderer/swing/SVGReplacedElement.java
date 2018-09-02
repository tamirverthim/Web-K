package org.xhtmlrenderer.swing;

import com.kitfox.svg.SVGCache;
import com.kitfox.svg.app.beans.SVGPanel;
import lombok.val;
import org.xhtmlrenderer.dom.nodes.Element;
import org.xhtmlrenderer.util.GeneralUtil;

import java.io.StringReader;
import java.util.UUID;

/**
 * @author Taras Maslov
 * 8/30/2018
 */
public class SVGReplacedElement extends SwingReplacedElement {
    
    Element model;
    
    public SVGReplacedElement(Element svgElement) {
        super(new SVGPanel());
        this.model = svgElement;
        initialize();
    }

    private void initialize() {
        val svgPanel = (SVGPanel)getJComponent();
        val w = GeneralUtil.parseIntRelaxed(model.attr("width"));
        val h = GeneralUtil.parseIntRelaxed(model.attr("height"));
        svgPanel.setSize(w, h);
        // Use an instanse of ourselves as the SAX event handler
        val svgCode = model.outerHtml();
        val name = UUID.randomUUID().toString();
        val uri = SVGCache.getSVGUniverse().loadSVG(new StringReader(svgCode), name, false);
        svgPanel.setSvgURI(uri);
    }

    @Override
    public boolean isRequiresInteractivePaint() {
        return true;
    }
}
