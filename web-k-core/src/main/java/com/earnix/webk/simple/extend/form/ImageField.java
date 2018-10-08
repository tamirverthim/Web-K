/*
 * {{{ header & license
 * Copyright (c) 2007 Sean Bright
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package com.earnix.webk.simple.extend.form;

import com.earnix.webk.css.constants.CSSName;
import com.earnix.webk.css.style.CalculatedStyle;
import com.earnix.webk.css.style.FSDerivedValue;
import com.earnix.webk.css.style.derived.LengthValue;
import com.earnix.webk.dom.nodes.Element;
import com.earnix.webk.extend.FSImage;
import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;
import com.earnix.webk.simple.extend.XhtmlForm;
import com.earnix.webk.swing.AWTFSImage;
import com.earnix.webk.util.XRLog;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageField extends InputField {
    public ImageField(Element e, XhtmlForm form, LayoutContext context, BlockBox box) {
        super(e, form, context, box);
    }

    public JComponent create() {
        JButton button;
        Image image = null;

        if (hasAttribute("src")) {
            FSImage fsImage = getUserAgentCallback().getImageResource(getAttribute("src")).getImage();

            if (fsImage != null) {
                image = ((AWTFSImage) fsImage).getImage();
            }
        }

        if (image == null) {
            button = new JButton("Image unreachable. " + getAttribute("alt"));
        } else {
            final ImageIcon imgIcon = new ImageIcon(image, getAttribute("alt"));
            final Image img = imgIcon.getImage();
            button = new JButton() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                }

                public Dimension getPreferredSize() {
                    return new Dimension(imgIcon.getIconWidth(), imgIcon.getIconHeight());
                }
            };
        }

        button.setUI(new BasicButtonUI());
        button.setContentAreaFilled(false);


        CalculatedStyle style = getStyle();

        FSDerivedValue widthValue = style.valueByName(CSSName.WIDTH);
        if (widthValue instanceof LengthValue) {
            intrinsicWidth = new Integer(getBox().getContentWidth());
        }

        FSDerivedValue heightValue = style.valueByName(CSSName.HEIGHT);
        if (heightValue instanceof LengthValue) {
            intrinsicHeight = new Integer(getBox().getHeight());
        }

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                XRLog.layout("Image pressed: Submit");

                getParentForm().submit(getComponent());
            }
        });

        return button;
    }
}
