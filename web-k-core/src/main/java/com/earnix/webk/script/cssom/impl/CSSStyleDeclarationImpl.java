package com.earnix.webk.script.cssom.impl;

import com.earnix.webk.css.constants.CSSName;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.cssom.CSSRule;
import com.earnix.webk.script.cssom.CSSStyleDeclaration;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.swing.BasicPanel;
import com.earnix.webk.util.XRLog;
import com.helger.css.ECSSVersion;
import com.helger.css.reader.CSSReaderDeclarationList;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.LinkedHashMap;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CSSStyleDeclarationImpl implements CSSStyleDeclaration {

    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    ElementModel model;

    public CSSStyleDeclarationImpl(ElementModel model, ScriptContext panel) {
        this(model.attr("style"), panel);
        this.model = model;
    }

    public CSSStyleDeclarationImpl(String css, ScriptContext ctx) {
        setCSSText(css);
    }

    @Override
    public Attribute<String> cssText() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return toCSSString();
            }

            @Override
            public void set(String s) {
                setCSSText(s);
                synchronize();
            }
        };
    }

    @Override
    public int length() {
        return map.size();
    }

    @Override
    public String item(int index) {
        try {
            return (String) map.values().toArray()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            XRLog.script(java.util.logging.Level.WARNING, "No such item");
            return null;
        }
    }

    @Override
    public String getPropertyValue(String property) {
        val res = map.get(property);
        if (res == null) {
            try {
                return CSSName.initialValue(CSSName.getByPropertyName(property));
            } catch (NullPointerException e) {
                return "";
            }
        } else {
            return res;
        }
    }

    @Override
    public String getPropertyPriority(String property) {
        return null;
    }

    @Override
    public void setProperty(String property, String value, String priority) {
        map.put(toKebabCase(priority), value);
        synchronize();
    }

    @Override
    public String removeProperty(String property) {
        val res = map.remove(property);
        synchronize();
        return res;
    }

    @Override
    public CSSRule parentRule() {
        return null;
    }

    @Override
    public Attribute<String> cssFloat() {
        return null;
    }

    public String toCSSString() {
        return map.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue() + ";").reduce("", (a, b) -> a + " \n " + b);
    }
    
    public void setCSSText(String css){
        map.clear();
        val declarations = CSSReaderDeclarationList.readFromString(css, ECSSVersion.CSS30);
        if (declarations != null) {
            declarations.forEach(d -> {
                map.put(d.getProperty(), d.getExpressionAsCSSString());
            });
        }
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

    private void synchronize() {
        if (model != null) {
            model.attr("style", toCSSString());
        }
    }
}
