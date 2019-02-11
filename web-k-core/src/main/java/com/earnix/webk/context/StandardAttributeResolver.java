/*
 * {{{ header & license
 * Copyright (c) 2004, 2005 Torbjoern Gannholm
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
package com.earnix.webk.context;

import com.earnix.webk.css.extend.AttributeResolver;
import com.earnix.webk.extend.NamespaceHandler;
import com.earnix.webk.extend.UserAgentCallback;
import com.earnix.webk.extend.UserInterface;
import com.earnix.webk.runtime.whatwg_dom.impl.ElementImpl;

/**
 * An instance which works together with a w3c DOM tree
 *
 * @author Torbjoern Gannholm
 */
public class StandardAttributeResolver implements AttributeResolver {
    private NamespaceHandler nsh;
    private UserAgentCallback uac;
    private UserInterface ui;

    public StandardAttributeResolver(NamespaceHandler nsh, UserAgentCallback uac, UserInterface ui) {
        this.nsh = nsh;
        this.uac = uac;
        this.ui = ui;
    }

    /**
     * Gets the attributeValue attribute of the StandardAttributeResolver object
     *
     * @param e        PARAM
     * @param attrName PARAM
     * @return The attributeValue value
     */
    public String getAttributeValue(Object e, String attrName) {
        return nsh.getAttributeValue((ElementImpl) e, attrName);
    }

    public String getAttributeValue(Object e, String namespaceURI, String attrName) {
        return nsh.getAttributeValue((ElementImpl) e, namespaceURI, attrName);
    }

    /**
     * Gets the class attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The class value
     */
    public String getClass(Object e) {
        return nsh.getClass((ElementImpl) e);
    }

    /**
     * Gets the iD attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The iD value
     */
    public String getID(Object e) {
        return nsh.getID((ElementImpl) e);
    }

    public String getNonCssStyling(Object e) {
        return nsh.getNonCssStyling((ElementImpl) e);
    }

    /**
     * Gets the elementStyling attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The elementStyling value
     */
    public String getElementStyling(Object e) {
        return nsh.getElementStyling((ElementImpl) e);
    }

    /**
     * Gets the lang attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The lang value
     */
    public String getLang(Object e) {
        return nsh.getLang((ElementImpl) e);
    }

    /**
     * Gets the link attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The link value
     */
    public boolean isLink(Object e) {
        return nsh.getLinkUri((ElementImpl) e) != null;
    }

    /**
     * Gets the visited attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The visited value
     */
    public boolean isVisited(Object e) {
        return isLink(e) && uac.isVisited(nsh.getLinkUri((ElementImpl) e));
    }

    /**
     * Gets the hover attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The hover value
     */
    public boolean isHover(Object e) {
        return ui.isHover((ElementImpl) e);
    }

    /**
     * Gets the active attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The active value
     */
    public boolean isActive(Object e) {
        return ui.isActive((ElementImpl) e);
    }

    /**
     * Gets the focus attribute of the StandardAttributeResolver object
     *
     * @param e PARAM
     * @return The focus value
     */
    public boolean isFocus(Object e) {
        return ui.isFocus((ElementImpl) e);
    }
}

