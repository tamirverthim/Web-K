package org.xhtmlrenderer.js;

import org.xhtmlrenderer.simple.XHTMLPanel;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import static javax.script.ScriptContext.ENGINE_SCOPE;

/**
 * @author Taras Maslov
 * 5/21/2018
 */
public class JS {
    private final ScriptEngine engine;
    private final ScriptContext context;
    
    private XHTMLPanel panel;
    private JsConsole console = new JsConsole();
    private JsWindow window = new JsWindow();
    
    @FunctionalInterface
    public interface SetInterval {
        void setInterval(Consumer<Object> fn, int interval);
    }    
    
    @FunctionalInterface
    public interface SetTimeout {
        void setTimeout(Consumer<Object> fn, int interval);
    }
    
    public class JsWindow implements SetInterval {
        
        public void setInterval(Consumer<Object> fn, int interval) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        fn.accept(null);
                        panel.setDocument(panel.getDocument());
                    });
                }
            }, 0, interval);
        }
    }
    
    
    public JS(XHTMLPanel panel) {
        this.panel = panel;
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        context = engine.getContext();
        context.setAttribute("document", panel.getDocument(), ENGINE_SCOPE);
        context.setAttribute("console", console, ENGINE_SCOPE);
        context.setAttribute("setInterval", window, ENGINE_SCOPE);
        try {
            context.setAttribute("window", engine.eval("this"), ENGINE_SCOPE);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
//        eval("this.setInterval = __rebind.setInterval;");
    }
    
    public void eval(String scr){
        try {
            engine.eval(scr, context);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        panel.repaint();
    }
    

}
