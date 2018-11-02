package com.earnix.webk.script.whatwg_dom.css_style_attribute;

import com.earnix.webk.css.constants.CSSName;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.FunctionAdapter;
import com.earnix.webk.script.web_idl.Function;
import com.earnix.webk.swing.BasicPanel;
import com.helger.css.ECSSVersion;
import com.helger.css.reader.CSSReaderDeclarationList;
import jdk.nashorn.api.scripting.AbstractJSObject;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import netscape.javascript.JSException;

import java.util.HashMap;

/**
 * @author Taras Maslov
 * 7/25/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CSSStyleAttribute extends AbstractJSObject {

    final BasicPanel panel;
    HashMap<String, String> map = new HashMap<>();
    ElementModel model;

    public CSSStyleAttribute(ElementModel model, BasicPanel panel) {
        this(model.attr("style"), panel);
        this.model = model;
    }

    public CSSStyleAttribute(String css, BasicPanel panel) {
        this.panel = panel;
        val declarations = CSSReaderDeclarationList.readFromString(css, ECSSVersion.CSS30);
        if (declarations != null) {
            declarations.forEach(d -> {
                map.put(d.getProperty(), d.getExpressionAsCSSString());
            });
        }
    }

    @Override
    public Object getMember(String name) throws JSException {
        if (name.equals("getPropertyValue")) {
            return new FunctionAdapter<>(panel.getScriptContext(), (Function<Object>) (ctx, arg) -> {
                val res = map.get(arg[0]);
                if (res == null) {
                    try {
                        return CSSName.initialValue(CSSName.getByPropertyName((String) arg[0]));
                    } catch (NullPointerException e) {
                        return "";
                    }
                } else {
                    return res;
                }
            }, "getPropertyValue");
        }
        val res = map.get(name);
        if (res == null) {
            return "";
        } else {
            return res;
        }
    }

    @Override
    public void setMember(String name, Object value) throws JSException {

        map.put(toKebabCase(name), value.toString());
        synchronize();
    }

    @Override
    public void removeMember(String name) throws JSException {
        map.remove(toKebabCase(name));
        synchronize();
    }

    @Override
    public Object getSlot(int index) throws JSException {
        return null;
    }

    @Override
    public void setSlot(int index, Object value) throws JSException {

    }

    private void synchronize() {
        if (model != null) {
            model.attr("style", toCSSString());
        }
    }

    public String toCSSString() {
        return map.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue() + ";").reduce("", (a, b) -> a + " \n " + b);
    }

    private String toKebabCase(String camel) {
        StringBuilder result = new StringBuilder();
        for (char c : camel.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (result.length() > 0) {
                    result.append("-");
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
