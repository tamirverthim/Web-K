/*
 * {{{ header & license
 * Copyright (c) 2004, 2005 Joshua Marinacci
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package com.earnix.kbrowser.simple;

import com.earnix.kbrowser.layout.SharedContext;
import org.w3c.dom.Document;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


/**
 * <p/>
 * Graphics2DRenderer supports headless rendering of XHTML documents, and is useful
 * for rendering documents directly to images.</p>
 * <p/>
 * <p>Graphics2DRenderer supports the {@link XHTMLPanel#setDocument(Document)},
 * {@link XHTMLPanel#layout()}, and {@link XHTMLPanel#render()} methods from
 * {@link XHTMLPanel}, as well as easy-to-use static utility methods.
 * For example, to render a document in an image that is 600 pixels wide use the
 * {@link #renderToImageAutoSize(String, int, int)} method like this:</p>
 * <pre>
 * BufferedImage img = Graphics2DRenderer.renderToImage( "test.xhtml", width);
 * </pre>
 * <p/>
 * <p/>
 * <p/>
 * </p>
 *
 * @author Joshua Marinacci
 */

public class Graphics2DRenderer {
    /**
     * The panel we are using to render the document.
     */
    protected XHTMLPanel panel;

    /**
     * Dimensions of the image to render, in pixels.
     */
    protected Dimension dim;

    /**
     * Creates a new renderer with no document specified.
     */
    public Graphics2DRenderer() {
        panel = new XHTMLPanel();
        panel.setInteractive(false);
    }

    // ASK maybe we could change the graphics2d to be a font rendering context?

    /**
     * Lay out the document with the specified dimensions,
     * without rendering.
     *
     * @param g2  the canvas to layout on.
     * @param dim dimensions of the container for the document
     */
    public void layout(Graphics2D g2, Dimension dim) {
        this.dim = dim;
        if (dim != null) {
            panel.setSize(dim);
        }
        panel.doDocumentLayout(g2);
    }


    /**
     * Renders the document to the given canvas. Call layout() first.
     *
     * @param g2 Canvas to render to.
     */
    public void render(Graphics2D g2) {
        if (g2.getClip() == null) {
            g2.setClip(getMinimumSize());
        }
        panel.paintComponent(g2);
    }


    /**
     * Set the document to be rendered, lays it out, and
     * renders it.
     *
     * @param url the URL for the document to render.
     */
    public void setDocument(String url) {
        panel.setDocument(url);
    }

    /**
     * Sets the document to render, lays it out, and renders it.
     *
     * @param doc      the Document to render
     * @param base_url base URL for relative links within the Document.
     */
    public void setDocument(com.earnix.kbrowser.dom.nodes.Document doc, String base_url) {
        panel.setDocument(doc, base_url);
    }

    /**
     * Sets the SharedContext for rendering.
     *
     * @param ctx The new renderingContext value
     */
    public void setSharedContext(SharedContext ctx) {
        panel.setSharedContext(ctx);
    }

    /**
     * Returns the size image needed to render the document without anything
     * going off the side. Could be different than the dimensions passed into
     * layout because of text that couldn't break or a table that's set to be
     * too big.
     *
     * @return A rectangle sized to the minimum size required for the
     * document.
     */
    public Rectangle getMinimumSize() {
        if (panel.getPreferredSize() != null) {
            return new Rectangle(0, 0,
                    (int) panel.getPreferredSize().getWidth(),
                    (int) panel.getPreferredSize().getHeight());
        } else {
            return new Rectangle(0, 0, panel.getWidth(), panel.getHeight());
        }
    }

    /**
     * Gets the SharedContext for layout and rendering.
     *
     * @return see desc
     */
    public SharedContext getSharedContext() {
        return panel.getSharedContext();
    }

    /**
     * Returns the panel used internally for rendering.
     */
    public XHTMLPanel getPanel() {
        return panel;
    }

    /**
     * A static utility method to automatically create an image from a
     * document; the image supports transparency. To render an image that does not support transparency,
     * use the overloaded version of this method {@link #renderToImage(String, int, int, int)}.
     *
     * @param url    URL for the document to render.
     * @param width  Width in pixels of the layout container
     * @param height Height in pixels of the layout container
     * @return Returns an Image containing the rendered document.
     */
    public static BufferedImage renderToImage(String url, int width, int height) {
        return renderToImage(url, width, height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * A static utility method to automatically create an image from a
     * document. The buffered image type must be specified.
     *
     * @param url               URL for the document to render.
     * @param width             Width in pixels of the layout container
     * @param height            Height in pixels of the layout container
     * @param bufferedImageType On of the pre-defined image types for a java.awt.image.BufferedImage, such
     *                          as TYPE_INT_ARGB or TYPE_INT_RGB.
     * @return Returns an Image containing the rendered document.
     */
    public static BufferedImage renderToImage(String url, int width, int height, int bufferedImageType) {
        Graphics2DRenderer g2r = new Graphics2DRenderer();
        g2r.setDocument(url);
        Dimension dim = new Dimension(width, height);
        BufferedImage buff = new BufferedImage((int) dim.getWidth(), (int) dim.getHeight(), bufferedImageType);
        Graphics2D g = (Graphics2D) buff.getGraphics();
        g2r.layout(g, dim);
        g2r.render(g);
        g.dispose();
        return buff;
    }

    /**
     * A static utility method to automatically create an image from a
     * document, where height is determined based on document content.
     * To estimate a size before rendering, use {@link #setDocument(String)}
     * and then {@link #getMinimumSize()}. The rendered image supports transparency.
     *
     * @param url   java.net.URL for the document to render.
     * @param width Width in pixels of the layout container
     * @return Returns an java.awt.Image containing the rendered document.
     */
    public static BufferedImage renderToImageAutoSize(String url, int width) {
        return renderToImageAutoSize(url, width, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * A static utility method to automatically create an image from a
     * document, where height is determined based on document content.
     * To estimate a size before rendering, use {@link #setDocument(String)}
     * and then {@link #getMinimumSize()}.
     *
     * @param url               java.net.URL for the document to render.
     * @param width             Width in pixels of the layout container
     * @param bufferedImageType On of the pre-defined image types for a java.awt.image.BufferedImage, such
     *                          as TYPE_INT_ARGB or TYPE_INT_RGB.
     * @return Returns an java.awt.Image containing the rendered document.
     */
    public static BufferedImage renderToImageAutoSize(String url, int width, int bufferedImageType) {
        Graphics2DRenderer g2r = new Graphics2DRenderer();
        g2r.setDocument(url);
        Dimension dim = new Dimension(width, 1000);

        // do layout with temp buffer
        BufferedImage buff = new BufferedImage((int) dim.getWidth(), (int) dim.getHeight(), bufferedImageType);
        Graphics2D g = (Graphics2D) buff.getGraphics();
        g2r.layout(g, new Dimension(width, 1000));
        g.dispose();

        // get size
        Rectangle rect = g2r.getMinimumSize();

        // render into real buffer
        buff = new BufferedImage((int) rect.getWidth(), (int) rect.getHeight(), bufferedImageType);
        g = (Graphics2D) buff.getGraphics();
        g2r.render(g);
        g.dispose();

        // return real buffer
        return buff;
    }
}
