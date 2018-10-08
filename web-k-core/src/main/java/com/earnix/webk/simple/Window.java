//package com.earnix.webk.simple;
//
//import jdk.nashorn.api.scripting.NashornScriptEngine;
//import org.w3c.dom.NodeList;
//import com.earnix.webk.event.DocumentListener;
//import com.earnix.webk.js.JsDocument;
//
//import javax.script.*;
//import javax.swing.*;
//import java.awt.*;
//
///**
// * @author Taras Maslov
// * 5/21/2018
// */
//public class Window {
//    
//    private XHTMLPanel panel;
//    private ScriptEngine engine;
//    
//    
//
//    private final ScriptContext context;
//
//    {
//        panel = new XHTMLPanel();
//        panel.addDocumentListener(new DocumentListener() {
//            @Override
//            public void documentStarted() {
//                
//            }
//
//            @Override
//            public void documentLoaded() {
//                
//              
//            }
//
//            @Override
//            public void onLayoutException(Throwable t) {
//
//            }
//
//            @Override
//            public void onRenderException(Throwable t) {
//
//            }
//        });
//        engine = new ScriptEngineManager().getEngineByName("nashorn");
//      
//    }
//
//    public JPanel getPanel() {
//        return panel;
//    }
//
//    public class Console {
//        public void log(String s){
//            System.err.println(s);
//        }
//    }
//    
//    public void loadUrl(String url){
//        panel.setDocument(url);
//    }
//}
