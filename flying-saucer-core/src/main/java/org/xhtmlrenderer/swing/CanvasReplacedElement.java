package org.xhtmlrenderer.swing;

import lombok.AllArgsConstructor;
import org.xhtmlrenderer.js.canvas.impl.CanvasRenderingContext2DImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public class CanvasReplacedElement extends SwingReplacedElement {

    private CanvasRenderingContext2DImpl impl;

    public CanvasReplacedElement(CanvasRenderingContext2DImpl impl) {
        super(new CanvasPanel(impl.getBufferedImage()));
    }


    @AllArgsConstructor
    static class CanvasPanel extends JPanel {

        private BufferedImage bufferedImage;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bufferedImage, 0, 0, null);
        }
    }

}
