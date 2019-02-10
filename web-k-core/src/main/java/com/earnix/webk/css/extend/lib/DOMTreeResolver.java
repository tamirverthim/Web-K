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
import com.earnix.webk.runtime.impl.ElementImpl;
import com.earnix.webk.runtime.impl.NodeImpl;

import java.util.List;


/**
 * @author scott
 * <p/>
 * works for a w3c DOM tree
 */
public class DOMTreeResolver implements TreeResolver {
    public Object getParentElement(Object element) {
        NodeImpl parent = ((ElementImpl) element).parentNode();
        if (!(parent instanceof ElementImpl)) parent = null;
        return parent;
    }

    public Object getPreviousSiblingElement(Object element) {
        NodeImpl sibling = ((ElementImpl) element).previousSibling();
        while (sibling != null && !(sibling instanceof ElementImpl)) {
            sibling = sibling.previousSibling();
        }
        if (sibling == null) {
            return null;
        }
        return sibling;
    }

    public String getElementName(Object element) {
        return ((ElementImpl) element).nodeName();
    }

    public boolean isFirstChildElement(Object element) {
        NodeImpl parent = ((ElementImpl) element).parentNode();
        NodeImpl currentChild;
        if (parent.childNodeSize() > 0) {
            currentChild = parent.childNode(0);
        } else {
            currentChild = null;
        }

        while (currentChild != null && !(currentChild instanceof ElementImpl)) {
            currentChild = currentChild.nextSibling();
        }
        return currentChild == element;
    }

    public boolean isLastChildElement(Object element) {
        NodeImpl parent = ((ElementImpl) element).parentNode();
        NodeImpl currentChild = parent.childNode(parent.childNodeSize() - 1);
        while (currentChild != null && !(currentChild instanceof ElementImpl)) {
            currentChild = currentChild.previousSibling();
        }
        return currentChild == element;
    }

    public boolean matchesElement(Object element, String namespaceURI, String name) {
        return ((ElementImpl) element).nodeName().equalsIgnoreCase(name);
    }

    public int getPositionOfElement(Object element) {
        NodeImpl parent = ((ElementImpl) element).parent();
        List<NodeImpl> nl = parent.getChildNodes();

        int elt_count = 0;
        int i = 0;
        while (i < nl.size()) {
            if (nl.get(i) instanceof ElementImpl) {
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
