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

package com.earnix.webk.css.extend.lib;


import com.earnix.webk.css.extend.AttributeResolver;
import com.earnix.webk.script.impl.ElementImpl;
import lombok.val;

/**
 * Works for Xhtml in a DOM tree
 */
public class DOMStaticXhtmlAttributeResolver implements AttributeResolver {

    public String getAttributeValue(Object e, String attrName) {
        val element = (ElementImpl) e;
        return element.attr(attrName);

    }

    public String getAttributeValue(Object o, String namespaceURI, String attrName) {
        ElementImpl e = (ElementImpl) o;
        return e.attr(attrName);

    }

    public String getClass(Object e) {
        return getAttributeValue(e, "class");
    }

    public String getID(Object e) {
        return getAttributeValue(e, "id");
    }

    public String getNonCssStyling(Object e) {
        return null;
    }

    public String getLang(Object e) {
        return getAttributeValue(e, "lang");
    }

    public String getElementStyling(Object el) {
        ElementImpl e = ((ElementImpl) el);
        StringBuffer style = new StringBuffer();
        if (e.nodeName().equals("td")) {
            String s;
            if (!(s = e.attr("colspan")).equals("")) {
                style.append("-fs-table-cell-colspan: ");
                style.append(s);
                style.append(";");
            }
            if (!(s = e.attr("rowspan")).equals("")) {
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
        ElementImpl e = ((ElementImpl) el);
        return e.nodeName().equalsIgnoreCase("a") && !e.attr("href").isEmpty();
    }

    public boolean isVisited(Object e) {
        return false;
    }

}
