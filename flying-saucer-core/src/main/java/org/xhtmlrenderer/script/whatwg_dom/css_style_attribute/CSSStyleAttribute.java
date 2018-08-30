package org.xhtmlrenderer.script.whatwg_dom.css_style_attribute;

import com.helger.css.ECSSVersion;
import com.helger.css.reader.CSSReaderDeclarationList;
import jdk.nashorn.api.scripting.AbstractJSObject;
import lombok.val;
import netscape.javascript.JSException;
import org.xhtmlrenderer.script.Function;

import java.util.HashMap;

/**
 * @author Taras Maslov
 * 7/25/2018
 */
public class CSSStyleAttribute extends AbstractJSObject {

    HashMap<String, String> map = new HashMap<>();

    public CSSStyleAttribute(String css) {
        val declarations = CSSReaderDeclarationList.readFromString(css, ECSSVersion.CSS30);
        
        declarations.forEach(d -> {
            map.put(d.getProperty(), d.getExpressionAsCSSString());
        });
        
//        val declaration = declarations.getDeclarationAtIndex(0);

        
        
        // Get the Shorthand descriptor for "border"    
//        final CSSShortHandDescriptor aSHD = CSSShortHandRegistry.getShortHandDescriptor (ECSSProperty.FONT);
//
//        // And now split it into pieces
//        final List<CSSDeclaration> aSplittedDecls = aSHD.getSplitIntoPieces (declaration);
//        for (CSSDeclaration aSplittedDecl : aSplittedDecls) {
//
//            val fontSize = aSplittedDecl.getExpression().getAsCSSString();
//            if(fontSize.endsWith("px")){
//                state().setFontSize((int) Math.round(Double.parseDouble(fontSize.substring(0, 2))));
//                stateDirty = true;
//            }
//
//        }
    }

    //    @Override
//    public Object call(String methodName, Object... args) throws JSException {
//        return null;
//    }

//    @Override
//    public Object eval(String s) throws JSException {
//        return null;
//    }

    @Override
    public Object getMember(String name) throws JSException {
        if(name.equals("getPropertyValue")){
            return new Function<>((Function.Callback<Object>) (ctx, arg) -> map.get(arg[0]), "getPropertyValue");
        }
        return map.get(name);
    }

    @Override
    public void setMember(String name, Object value) throws JSException {
        map.put(name, value.toString());
    }

    @Override
    public void removeMember(String name) throws JSException {
        map.remove(name);
    }

    @Override
    public Object getSlot(int index) throws JSException {
        return null;
    }

    @Override
    public void setSlot(int index, Object value) throws JSException {

    }

    public String toCSSString() {
        return map.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue() + ";").reduce("", (a, b) -> a + " \n " + b);
    }
}
