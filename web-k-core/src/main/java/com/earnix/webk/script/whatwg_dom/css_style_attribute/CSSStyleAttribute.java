//package com.earnix.webk.script.whatwg_dom.css_style_attribute;
//
//import com.earnix.webk.css.constants.CSSName;
//import com.earnix.webk.dom.nodes.ElementModel;
//import com.earnix.webk.script.FunctionAdapter;
//import com.earnix.webk.script.web_idl.Function;
//import com.earnix.webk.swing.BasicPanel;
//import com.helger.css.ECSSVersion;
//import com.helger.css.reader.CSSReaderDeclarationList;
//import jdk.nashorn.api.scripting.AbstractJSObject;
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//import lombok.val;
//import netscape.javascript.JSException;
//
//import java.util.HashMap;
//
///**
// * @author Taras Maslov
// * 7/25/2018
// */
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class CSSStyleAttribute extends AbstractJSObject {
//
//    final BasicPanel panel;
//    ElementModel model;
//
//    
//
//    @Override
//    public Object getMember(String name) throws JSException {
//        if (name.equals("getPropertyValue")) {
//            return new FunctionAdapter<>(panel.getScriptContext(), (Function<Object>) (ctx, arg) -> {
//               
//            }, "getPropertyValue");
//        }
////        val res = map.get(name);
////        if (res == null) {
////            return "";
////        } else {
////            return res;
////        }
//    }
//
//    @Override
//    public void setMember(String name, Object value) throws JSException {
//
//        synchronize();
//    }
//
//    @Override
//    public void removeMember(String name) throws JSException {
//        map.remove(toKebabCase(name));
//        synchronize();
//    }
//
//    @Override
//    public Object getSlot(int index) throws JSException {
//        return null;
//    }
//
//    @Override
//    public void setSlot(int index, Object value) throws JSException {
//
//    }
//
//   
//
//   
//}
