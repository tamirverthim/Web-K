/*
 * DOMTreeResolver.java
 * Copyright (c) 2005 Scott Cytacki
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


import com.earnix.webk.css.extend.TreeResolver;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.nodes.NodeModel;

import java.util.List;

/**
 * @author scott
 * <p/>
 * works for a w3c DOM tree
 */
public class DOMTreeResolver implements TreeResolver {
    public Object getParentElement(Object element) {
        NodeModel parent = ((ElementModel) element).parentNode();
        if (!(parent instanceof ElementModel)) parent = null;
        return parent;
    }

    public Object getPreviousSiblingElement(Object element) {
        NodeModel sibling = ((ElementModel) element).previousSibling();
        while (sibling != null && !(sibling instanceof ElementModel)) {
            sibling = sibling.previousSibling();
        }
        if (sibling == null) {
            return null;
        }
        return sibling;
    }

    public String getElementName(Object element) {
        return ((ElementModel) element).nodeName();
    }

    public boolean isFirstChildElement(Object element) {
        NodeModel parent = ((ElementModel) element).parentNode();
        NodeModel currentChild;
        if (parent.childNodeSize() > 0) {
            currentChild = parent.childNode(0);
        } else {
            currentChild = null;
        }

        while (currentChild != null && !(currentChild instanceof ElementModel)) {
            currentChild = currentChild.nextSibling();
        }
        return currentChild == element;
    }

    public boolean isLastChildElement(Object element) {
        NodeModel parent = ((ElementModel) element).parentNode();
        NodeModel currentChild = parent.childNode(parent.childNodeSize() - 1);
        while (currentChild != null && !(currentChild instanceof ElementModel)) {
            currentChild = currentChild.previousSibling();
        }
        return currentChild == element;
    }

    public boolean matchesElement(Object element, String namespaceURI, String name) {
        return ((ElementModel) element).nodeName().equalsIgnoreCase(name);
    }

    public int getPositionOfElement(Object element) {
        NodeModel parent = ((ElementModel) element).parent();
        List<NodeModel> nl = parent.childNodes();

        int elt_count = 0;
        int i = 0;
        while (i < nl.size()) {
            if (nl.get(i) instanceof ElementModel) {
                if (nl.get(i) == element) {
                    return elt_count;
                } else {
                    elt_count++;
                }
            }
            i++;
        }

        //should not happen
        throw new RuntimeException("getPositionOfElement out of bounds");
    }
}
