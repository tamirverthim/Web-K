/*
 * {{{ header & license
 * Copyright (c) 2007 Wisconsin Court System
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
package com.earnix.kbrowser.css.parser.property;

import com.earnix.kbrowser.css.constants.CSSName;
import com.earnix.kbrowser.css.constants.IdentValue;
import com.earnix.kbrowser.css.parser.CSSParseException;
import com.earnix.kbrowser.css.parser.FSFunction;
import com.earnix.kbrowser.css.parser.PropertyValue;
import com.earnix.kbrowser.css.sheet.PropertyDeclaration;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ContentPropertyBuilder extends AbstractPropertyBuilder {

    public List buildDeclarations(
            CSSName cssName, List values, int origin, boolean important, boolean inheritAllowed) {
        if (values.size() == 1) {
            PropertyValue value = (PropertyValue) values.get(0);
            if (value.getCssValueType() == CSSValue.CSS_INHERIT) {
                return Collections.EMPTY_LIST;
            } else if (value.getPrimitiveType() == CSSPrimitiveValue.CSS_IDENT) {
                IdentValue ident = checkIdent(CSSName.CONTENT, value);
                if (ident == IdentValue.NONE || ident == IdentValue.NORMAL) {
                    return Collections.singletonList(
                            new PropertyDeclaration(CSSName.CONTENT, value, important, origin));
                }
            }
        }

        List resultValues = new ArrayList();
        for (Iterator i = values.iterator(); i.hasNext(); ) {
            PropertyValue value = (PropertyValue) i.next();

            if (value.getOperator() != null) {
                throw new CSSParseException(
                        "Found unexpected operator, " + value.getOperator().getExternalName(), -1);
            }

            short type = value.getPrimitiveType();
            if (type == CSSPrimitiveValue.CSS_URI) {
                continue;
            } else if (type == CSSPrimitiveValue.CSS_STRING) {
                resultValues.add(value);
            } else if (value.getPropertyValueType() == PropertyValue.VALUE_TYPE_FUNCTION) {
                if (!isFunctionAllowed(value.getFunction())) {
                    throw new CSSParseException(
                            "Function " + value.getFunction().getName() + " is not allowed here", -1);
                }
                resultValues.add(value);
            } else if (type == CSSPrimitiveValue.CSS_IDENT) {
                IdentValue ident = checkIdent(CSSName.CONTENT, value);
                if (ident == IdentValue.OPEN_QUOTE || ident == IdentValue.CLOSE_QUOTE ||
                        ident == IdentValue.NO_CLOSE_QUOTE || ident == IdentValue.NO_OPEN_QUOTE) {
                    resultValues.add(value);
                } else {
                    throw new CSSParseException(
                            "Identifier " + ident + " is not a valid value for the content property", -1);
                }
            } else {
                throw new CSSParseException(
                        value.getCssText() + " is not a value value for the content property", -1);
            }
        }

        if (resultValues.size() > 0) {
            return Collections.singletonList(
                    new PropertyDeclaration(CSSName.CONTENT, new PropertyValue(resultValues), important, origin));
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private boolean isFunctionAllowed(FSFunction function) {
        String name = function.getName();
        return name.equals("attr") || name.equals("counter") || name.equals("counters") ||
                name.equals("element") || name.startsWith("-fs") || name.equals("target-counter") ||
                name.equals("leader");
    }
}
