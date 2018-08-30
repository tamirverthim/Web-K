//package org.xhtmlrenderer.script.impl;
//
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//import org.xhtmlrenderer.script.web_idl.DOMString;
//
//import java.util.WeakHashMap;
//
///**
// * @author Taras Maslov
// * 6/21/2018
// */
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class DOMStringImpl implements DOMString {
//    String string;
//
//    static WeakHashMap<String, DOMStringImpl> cache = new WeakHashMap<>();
//
//
//    private DOMStringImpl(String string) {
//        this.string = string;
//    }
//    
//    public static DOMStringImpl of(String string){
//        DOMStringImpl res = cache.get(string);
//        if(res == null){
//            res = new DOMStringImpl(string);
//            cache.put(string, res);
//        }
//        return res;
//    }
//
//    @Override
//    public String toString() {
//        return string;
//    }
//}
