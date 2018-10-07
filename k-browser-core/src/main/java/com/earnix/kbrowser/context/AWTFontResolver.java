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
package com.earnix.kbrowser.context;

import com.earnix.kbrowser.css.constants.IdentValue;
import com.earnix.kbrowser.css.value.FontSpecification;
import com.earnix.kbrowser.extend.FontResolver;
import com.earnix.kbrowser.layout.SharedContext;
import com.earnix.kbrowser.render.FSFont;
import com.earnix.kbrowser.swing.AWTFSFont;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;


/**
 * @author Joshua Marinacci
 */
public class AWTFontResolver implements FontResolver {

    HashMap instance_hash;
    HashMap available_fonts_hash;

    public AWTFontResolver() {
        init();
    }

    private void init() {
        GraphicsEnvironment gfx = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] available_fonts = gfx.getAvailableFontFamilyNames();
        instance_hash = new HashMap();

        // preload the font map with the font names as keys
        // don't add the actual font objects because that would be a waste of memory
        // we will only add them once we need to use them
        // put empty strings in instead
        available_fonts_hash = new HashMap();
        for (int i = 0; i < available_fonts.length; i++) {
            available_fonts_hash.put(available_fonts[i], "");
        }

        // preload sans, serif, and monospace into the available font hash
        available_fonts_hash.put("Serif", new Font("Serif", Font.PLAIN, 1));
        available_fonts_hash.put("SansSerif", new Font("SansSerif", Font.PLAIN, 1));
        available_fonts_hash.put("Monospaced", new Font("Monospaced", Font.PLAIN, 1));
    }

    public void flushCache() {
        init();
    }

    public FSFont resolveFont(SharedContext ctx, String[] families, float size, IdentValue weight, IdentValue style, IdentValue variant) {
        // for each font family
        if (families != null) {
            for (int i = 0; i < families.length; i++) {
                Font font = resolveFont(ctx, families[i], size, weight, style, variant);
                if (font != null) {
                    return new AWTFSFont(font);
                }
            }
        }

        // if we get here then no font worked, so just return default sans
        String family = "SansSerif";
        if (style == IdentValue.ITALIC) {
            family = "Serif";
        }

        Font fnt = createFont(ctx, (Font) available_fonts_hash.get(family), size, weight, style, variant);
        instance_hash.put(getFontInstanceHashName(ctx, family, size, weight, style, variant), fnt);
        return new AWTFSFont(fnt);
    }

    /**
     * Sets the fontMapping attribute of the FontResolver object
     *
     * @param name The new fontMapping value
     * @param font The new fontMapping value
     */
    public void setFontMapping(String name, Font font) {
        available_fonts_hash.put(name, font.deriveFont(1f));
    }

    protected static Font createFont(SharedContext ctx, Font root_font, float size, IdentValue weight, IdentValue style, IdentValue variant) {
        int font_const = Font.PLAIN;
        if (weight != null &&
                (weight == IdentValue.BOLD ||
                        weight == IdentValue.FONT_WEIGHT_700 ||
                        weight == IdentValue.FONT_WEIGHT_800 ||
                        weight == IdentValue.FONT_WEIGHT_900)) {

            font_const = font_const | Font.BOLD;
        }
        if (style != null && (style == IdentValue.ITALIC || style == IdentValue.OBLIQUE)) {
            font_const = font_const | Font.ITALIC;
        }

        // scale vs font scale value too
        size *= ctx.getTextRenderer().getFontScale();

        Font fnt = root_font.deriveFont(font_const, size);
        if (variant != null) {
            if (variant == IdentValue.SMALL_CAPS) {
                fnt = fnt.deriveFont((float) (((float) fnt.getSize()) * 0.6));
            }
        }

        return fnt;
    }

    protected Font resolveFont(SharedContext ctx, String font, float size, IdentValue weight, IdentValue style, IdentValue variant) {
        // strip off the "s if they are there
        if (font.startsWith("\"")) {
            font = font.substring(1);
        }
        if (font.endsWith("\"")) {
            font = font.substring(0, font.length() - 1);
        }

        // normalize the font name
        if (font.equalsIgnoreCase("serif")) {
            font = "Serif";
        }
        if (font.equalsIgnoreCase("sans-serif")) {
            font = "SansSerif";
        }
        if (font.equalsIgnoreCase("monospace")) {
            font = "Monospaced";
        }

        if (font.equals("Serif") && style == IdentValue.OBLIQUE) font = "SansSerif";
        if (font.equals("SansSerif") && style == IdentValue.ITALIC) font = "Serif";

        // assemble a font instance hash name
        String font_instance_name = getFontInstanceHashName(ctx, font, size, weight, style, variant);
        // check if the font instance exists in the hash table
        if (instance_hash.containsKey(font_instance_name)) {
            // if so then return it
            return (Font) instance_hash.get(font_instance_name);
        }

        // if not then
        //  does the font exist
        if (available_fonts_hash.containsKey(font)) {
            Object value = available_fonts_hash.get(font);
            // have we actually allocated the root font object yet?
            Font root_font = null;
            if (value instanceof Font) {
                root_font = (Font) value;
            } else {
                root_font = new Font(font, Font.PLAIN, 1);
                available_fonts_hash.put(font, root_font);
            }

            // now that we have a root font, we need to create the correct version of it
            Font fnt = createFont(ctx, root_font, size, weight, style, variant);

            // add the font to the hash so we don't have to do this again
            instance_hash.put(font_instance_name, fnt);
            return fnt;
        }

        // we didn't find any possible matching font, so just return null
        return null;
    }

    /**
     * Gets the fontInstanceHashName attribute of the FontResolverTest object
     *
     * @param ctx
     * @param name    PARAM
     * @param size    PARAM
     * @param weight  PARAM
     * @param style   PARAM
     * @param variant PARAM @return The fontInstanceHashName value
     */
    protected static String getFontInstanceHashName(SharedContext ctx, String name, float size, IdentValue weight, IdentValue style, IdentValue variant) {
        return name + "-" + (size * ctx.getTextRenderer().getFontScale()) + "-" + weight + "-" + style + "-" + variant;
    }

    public FSFont resolveFont(SharedContext renderingContext, FontSpecification spec) {
        return resolveFont(renderingContext, spec.families, spec.size, spec.fontWeight, spec.fontStyle, spec.variant);
    }
}