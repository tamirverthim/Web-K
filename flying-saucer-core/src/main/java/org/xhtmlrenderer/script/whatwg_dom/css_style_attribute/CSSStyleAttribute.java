package org.xhtmlrenderer.script.whatwg_dom.css_style_attribute;

import com.helger.css.ECSSVersion;
import com.helger.css.reader.CSSReaderDeclarationList;
import jdk.nashorn.api.scripting.AbstractJSObject;
import lombok.val;
import netscape.javascript.JSException;
import org.xhtmlrenderer.dom.nodes.Element;
import org.xhtmlrenderer.script.Function;

import java.util.HashMap;

/**
 * @author Taras Maslov
 * 7/25/2018
 */
public class CSSStyleAttribute extends AbstractJSObject {

    HashMap<String, String> map = new HashMap<>();
    Element model;
    
    public CSSStyleAttribute(Element model) {
        this(model.attr("style"));
        this.model = model;
    }
    
    public CSSStyleAttribute(String css) {
        val declarations = CSSReaderDeclarationList.readFromString(css, ECSSVersion.CSS30);
       if(declarations != null) {
           declarations.forEach(d -> {
               map.put(d.getProperty(), d.getExpressionAsCSSString());
           });
       }
    }

    @Override
    public Object getMember(String name) throws JSException {
        if(name.equals("getPropertyValue")){
            return new Function<>((Function.Callback<Object>) (ctx, arg) -> map.get(arg[0]), "getPropertyValue");
        }
        return map.get(name);
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
    
    private void synchronize(){
        if(model != null){
            model.attr("style", toCSSString());
        }
    } 

    public String toCSSString() {
        return map.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue() + ";").reduce("", (a, b) -> a + " \n " + b);
    }
    
    private String toKebabCase(String camel){
        StringBuilder result = new StringBuilder();
        for (char c : camel.toCharArray()) {
            if(Character.isUpperCase(c)){
                if(result.length() > 0){
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
