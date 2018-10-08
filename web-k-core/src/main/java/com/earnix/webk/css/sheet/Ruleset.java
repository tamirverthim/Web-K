/*
 * Ruleset.java
 * Copyright (c) 2004, 2005 Patrick Wright, Torbjoern Gannholm
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
package com.earnix.webk.css.sheet;

import com.earnix.webk.css.newmatch.Selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Torbjoern Gannholm
 * @author Patrick Wright
 */
public class Ruleset {
    private int _origin;
    private java.util.List _props;

    private List _fsSelectors = new ArrayList();

    public Ruleset(int orig) {
        _origin = orig;
        _props = new LinkedList();
        _fsSelectors = new LinkedList();
    }

    /**
     * Returns an Iterator of PropertyDeclarations pulled from this
     * CSSStyleRule.
     *
     * @return The propertyDeclarations value
     */
    public List getPropertyDeclarations() {
        return Collections.unmodifiableList(_props);
    }

    public void addProperty(PropertyDeclaration decl) {
        _props.add(decl);
    }

    public void addAllProperties(List props) {
        _props.addAll(props);
    }

    public void addFSSelector(Selector selector) {
        _fsSelectors.add(selector);
    }

    public List getFSSelectors() {
        return _fsSelectors;
    }

    public int getOrigin() {
        return _origin;
    }

}

