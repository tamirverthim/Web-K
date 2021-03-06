package com.earnix.webk.runtime.cssom.impl;

import com.earnix.webk.runtime.ScriptContext;
import com.earnix.webk.runtime.cssom.CSSRule;
import com.earnix.webk.runtime.cssom.CSSStyleDeclaration;
import com.earnix.webk.runtime.dom.impl.ElementImpl;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.dom.Element;
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

    private final ScriptContext context;
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    ElementImpl model;


    public CSSStyleDeclarationImpl(ElementImpl model, ScriptContext context) {
        this(model.attr("style"), context);
        this.model = model;
    }

    /**
     *
     * @param css
     * @param ctx
     *
     * @see com.earnix.webk.runtime.cssom.Window#getComputedStyle(Element, String)
     */
    public CSSStyleDeclarationImpl(String css, ScriptContext ctx) {
        setCSSText(css);
        this.context = ctx;
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
        val res = map.get(toKebabCase(property));
        if (res == null) {
            return "";
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
        map.put(toKebabCase(property), value);
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
        return map.entrySet()
                .stream()
                .map(e -> e.getKey() + ": " + e.getValue() + ";")
                .reduce("", (a, b) -> a + " \n " + b);
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
