package org.xhtmlrenderer.js;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.event.DefaultDocumentListener;
import org.xhtmlrenderer.js.dom.Document;
import org.xhtmlrenderer.js.dom.impl.DocumentImpl;
import org.xhtmlrenderer.net.Network;
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
@Slf4j
public class JS {
    private static JS instance;
    private ScriptEngine engine;
    private ScriptContext context;

    private XHTMLPanel panel;
    private JsConsole console = new JsConsole();
    private JsWindow window = new JsWindow();
    private org.w3c.dom.Document document;

    public void onload() {
        eval("window.onload && window.onload()");
    }

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

    private void initEngine() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        context = engine.getContext();
        context.setAttribute("document", new WebIDJAdapter<Document>(this, new DocumentImpl(panel.getDocument())), ENGINE_SCOPE);
        context.setAttribute("console", console, ENGINE_SCOPE);
        context.setAttribute("setInterval", window, ENGINE_SCOPE);
        context.setAttribute("location", new Location(), ENGINE_SCOPE);
        context.setAttribute("HTMLCanvasElement", new Location(), ENGINE_SCOPE);
        try {
            context.setAttribute("window", engine.eval("this"), ENGINE_SCOPE);
            context.setAttribute("self", engine.eval("this"), ENGINE_SCOPE);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    public JS(XHTMLPanel panel) {
        instance = this; // todo rem
        this.panel = panel;

//        eval("this.setInterval = __rebind.setInterval;");
        panel.addDocumentListener(new DefaultDocumentListener() {
            @Override
            public void documentLoaded() {
                val nextDocument = panel.getDocument();
                if (nextDocument != document) {
                    initEngine();
                    val scripts = panel.getDocument().getElementsByTagName("script");
                    log.trace("Document has {} scripts", scripts.getLength());
                    for (int i = 0; i < scripts.getLength(); i++) {
                        val script = scripts.item(i);
                        if (StringUtils.isNotBlank(script.getTextContent())) {
                            try {

                                log.trace("Evaluating script {} {}", System.lineSeparator(), script.getTextContent());
                                eval(script.getTextContent());
                            } catch (Exception e) {
                                log.warn("script.eval", e);
                            }
                        } else {
                            val scriptUri = script.getAttributes().getNamedItem("src");
                            if (StringUtils.isNotBlank(scriptUri.getTextContent())) {
                                try {
                                    val scriptText = Network.load(scriptUri.getTextContent());
                                    log.trace("Evaluating script {} {}", System.lineSeparator(), scriptText);
                                    eval(scriptText);
                                } catch (RuntimeException e) {
                                    log.debug("script.src", e);
                                }
                            }
                        }

                        onload();
                    }
                    document = nextDocument;
                }
            }
        });
    }

    public Object eval(String scr) {
        Object res;
        try {
            res = engine.eval(scr, context);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        panel.repaint();
        return res;
    }


    // temp
    public static JS getInstance() {
        return instance;
    }
}
