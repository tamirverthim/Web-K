/*
 *
 * DOMStaticXhtmlAttributeResolver.java
 * Copyright (c) 2004 Torbjoern Gannholm
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
 *
 */

package org.xhtmlrenderer.css.extend.lib;


import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.xhtmlrenderer.css.extend.AttributeResolver;
import org.xhtmlrenderer.css.extend.TreeResolver;

/**
 * Works for Xhtml in a DOM tree
 */
public class DOMStaticXhtmlAttributeResolver implements AttributeResolver {
    
    private static String toNullIfEmpty(String value) {
        return StringUtils.isNotBlank(value) ? value : null;
    }
    
    public String getAttributeValue(Object e, String attrName) {
        val value = ((Element) e).attr(attrName);
        return toNullIfEmpty(value);
    }
    
    public String getAttributeValue(Object o, String namespaceURI, String attrName) {
        Element e = (Element)o;
        val value = e.attr(attrName);
        return toNullIfEmpty(value);
    }

    public String getClass(Object e) {
        return toNullIfEmpty(((Element) e).attr("class"));
    }

    public String getID(Object e) {
        return toNullIfEmpty(((Element) e).id());
    }

    public String getNonCssStyling(Object e) {
        return null;
    }

    public String getLang(Object e) {
        return toNullIfEmpty(((Element) e).attr("lang"));
    }

    public String getElementStyling(Object el) {
        Element e = ((Element) el);
        StringBuilder style = new StringBuilder();
        if (e.nodeName().equals("td")) {
            String s = toNullIfEmpty(e.attr("colspan"));
            if (s != null) {
                style.append("-fs-table-cell-colspan: ");
                style.append(s);
                style.append(";");
            }
            s = toNullIfEmpty(e.attr("rowspan"));
            if (s != null) {
                style.append("-fs-table-cell-rowspan: ");
                style.append(s);
                style.append(";");
            }
        }
        style.append(e.attr("style"));
        return style.toString();
    }

    public boolean isActive(Object e) {
        return false;
    }

    public boolean isFocus(Object e) {
        return false;
    }

    public boolean isHover(Object e) {
        return false;
    }

    public boolean isLink(Object el) {
        Element e = ((Element) el);
        return e.nodeName().equalsIgnoreCase("a") && !e.attr("href").isEmpty();
    }

    public boolean isVisited(Object e) {
        return false;
    }

}
