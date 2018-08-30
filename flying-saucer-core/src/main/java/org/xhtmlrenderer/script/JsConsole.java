package org.xhtmlrenderer.script;

/**
 * @author Taras Maslov
 * 5/21/2018
 */
public class JsConsole {
    public void log(String text){
        System.out.println(text);
    }

    public void error(String text){
        System.err.println(text);
    }
}
