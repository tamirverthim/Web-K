/*
 * {{{ header & license
 * Copyright (c) 2007 Vianney le Clément
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
package com.earnix.webk.simple.xhtml.controls;

import com.earnix.webk.runtime.whatwg_dom.impl.ElementImpl;
import com.earnix.webk.runtime.whatwg_dom.impl.NodeImpl;
import com.earnix.webk.runtime.whatwg_dom.impl.TextImpl;
import com.earnix.webk.simple.xhtml.FormControl;
import com.earnix.webk.simple.xhtml.FormControlListener;
import com.earnix.webk.simple.xhtml.FormListener;
import com.earnix.webk.simple.xhtml.XhtmlForm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractControl implements FormControl {

    private XhtmlForm _form;
    private ElementImpl _element;
    private String _name;

    private String _initialValue;
    private String _value;
    private boolean _successful;
    private boolean _enabled;

    private List _listeners = new ArrayList();

    public AbstractControl(XhtmlForm form, ElementImpl e) {
        _form = form;
        _element = e;
        _name = e.attr("name");
        if (_name.length() == 0) {
            _name = e.attr("id");
        }
        _initialValue = e.attr("value");
        _value = _initialValue;
        _enabled = (e.hasAttr("disabled"));
        _successful = _enabled;

        if (form != null) {
            form.addFormListener(new FormListener() {
                public void submitted(XhtmlForm form) {
                }

                public void resetted(XhtmlForm form) {
                    reset();
                }
            });
        }
    }

    protected void fireChanged() {
        for (Iterator iter = _listeners.iterator(); iter.hasNext(); ) {
            ((FormControlListener) iter.next()).changed(this);
        }
    }

    protected void fireSuccessful() {
        for (Iterator iter = _listeners.iterator(); iter.hasNext(); ) {
            ((FormControlListener) iter.next()).successful(this);
        }
    }

    protected void fireEnabled() {
        for (Iterator iter = _listeners.iterator(); iter.hasNext(); ) {
            ((FormControlListener) iter.next()).enabled(this);
        }
    }

    public void addFormControlListener(FormControlListener listener) {
        _listeners.add(listener);
    }

    public void removeFormControlListener(FormControlListener listener) {
        _listeners.remove(listener);
    }

    public ElementImpl getElement() {
        return _element;
    }

    public XhtmlForm getForm() {
        return _form;
    }

    public String getName() {
        return _name;
    }

    public String getInitialValue() {
        return _initialValue;
    }

    protected void setInitialValue(String value) {
        _initialValue = value;
        _value = value;
    }

    public String getValue() {
        if (isMultiple()) {
            return null;
        } else {
            return _value;
        }
    }

    public void setValue(String value) {
        if (!isMultiple()) {
            _value = value;
            fireChanged();
        }
    }

    public String[] getMultipleValues() {
        return null;
    }

    public void setMultipleValues(String[] values) {
        // do nothing
    }

    public boolean isHidden() {
        return false;
    }

    public boolean isEnabled() {
        return _enabled;
    }

    public boolean isSuccessful() {
        return _successful && _enabled;
    }

    public boolean isMultiple() {
        return false;
    }

    public void setSuccessful(boolean successful) {
        _successful = successful;
        fireSuccessful();
    }

    public void setEnabled(boolean enabled) {
        _enabled = enabled;
        fireEnabled();
    }

    public void reset() {
        setValue(_initialValue);
    }

    public static String collectText(ElementImpl e) {
        StringBuffer result = new StringBuffer();
        NodeImpl node = e.childNodeSize() > 0 ? e.childNode(0) : null;
        if (node != null) {
            do {
                if (node instanceof TextImpl) {
                    TextImpl text = (TextImpl) node;
                    result.append(text.getWholeText());
                }
            } while ((node = node.nextSibling()) != null);
        }
        return result.toString().trim();
    }

    public static int getIntAttribute(ElementImpl e, String attribute, int def) {
        int result = def;
        String str = e.attr(attribute);
        if (str.length() > 0) {
            try {
                result = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
            }
        }
        return result;
    }

}
