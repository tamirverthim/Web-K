package com.earnix.webk.script;

import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.event.DocumentListener;
import com.earnix.webk.script.html.canvas.impl.CanvasGradientImpl;
import com.earnix.webk.script.html.canvas.impl.CanvasPatternImpl;
import com.earnix.webk.script.html.impl.DocumentImpl;
import com.earnix.webk.script.html.impl.WindowImpl;
import com.earnix.webk.script.ui_events.UIEventImpl;
import com.earnix.webk.script.ui_events.UIEventInit;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.whatwg_dom.impl.EventManager;
import com.earnix.webk.script.xhr.impl.XMLHttpRequestImpl;
import com.earnix.webk.swing.BasicPanel;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.URLReader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptException;

import static javax.script.ScriptContext.ENGINE_SCOPE;

/**
 * @author Taras Maslov
 * 5/21/2018
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScriptContext implements DocumentListener {
    //    static ScriptContext instance;
    NashornScriptEngine engine;
    javax.script.ScriptContext context;

    BasicPanel panel;
    DocumentModel document;
    EventManager eventManager;
    MouseEventsAdapter mouseEventsAdapter;


    WindowImpl window;
    private WebIDLAdapter<WindowImpl> windowAdapter;

    public ScriptContext(BasicPanel panel) {
        this.panel = panel;

        // initializing mouse events translation
        eventManager = new EventManager(this);
        mouseEventsAdapter = new MouseEventsAdapter(eventManager, panel);
        panel.addMouseListener(mouseEventsAdapter);
        panel.addMouseMotionListener(mouseEventsAdapter);
        panel.addMouseWheelListener(mouseEventsAdapter);
    }

    public void dispatchLoadEvents() {
//        eval("window.onload && window.onload()");

        //Type	load
        //Interface	UIEvent if generated from a user interface, Event otherwise.
        //Sync / Async	Async
        //Bubbles	No
        //Trusted Targets	Window, Document, Element
        //Cancelable	No
        //Default action	None
        //Context
        //(trusted events)	
        //Event.target : common object whose contained resources have loaded
        //UIEvent.view : Window
        //UIEvent.detail : 0
        
        document.walkElementsTree(e -> {
            val eventInit = new UIEventInit();
            eventInit.bubbles = false;
            eventInit.cancelable = false;
            eventInit.view = window;

//            eventInit.composed = false;
//        eventInit.view = getWindow() // todo

            val event = new UIEventImpl("load", eventInit);
            eventManager.publishEvent(e, event);
        });
        
        val eventInit = new UIEventInit();
        eventInit.bubbles = false;
        eventInit.cancelable = false;
        eventInit.view = window;
        eventManager.publishEvent(window, new UIEventImpl("load", eventInit));
//        eventManager.publishEvent(document, event);
    }
    
    private void dispatchUnloadEvents() {
        val eventInit = new UIEventInit();
        eventInit.bubbles = false;
        eventInit.cancelable = false;
        eventInit.view = window;
        eventManager.publishEvent(window, new UIEventImpl("unload", eventInit));
        
        document.walkElementsTree(e -> {
            val init = new UIEventInit();
            eventInit.bubbles = false;
            eventInit.cancelable = false;
            eventInit.view = window;

//            eventInit.composed = false;
//        eventInit.view = getWindow() // todo

            val event = new UIEventImpl("unload", eventInit);
            eventManager.publishEvent(e, event);
        });

      
    }

    public BasicPanel getPanel() {
        return panel;
    }

    @Override
    public void documentStarted() {
    }

    @Override
    public void documentLoaded() {
        handleNewDocument();
    }

    @Override
    public void onLayoutException(Throwable t) {
    }

    @Override
    public void onRenderException(Throwable t) {
    }

    private void initEngine() {
        String[] options = {"--language=es6", "--no-java"};
        NashornScriptEngineFactory jsFactory = new NashornScriptEngineFactory();
        engine = (NashornScriptEngine) jsFactory.getScriptEngine(options);
        context = engine.getContext();

        try {
            context.setAttribute("window", engine.eval("this"), ENGINE_SCOPE);
            context.setAttribute("self", engine.eval("this"), ENGINE_SCOPE);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        
        try {
            engine.eval(new URLReader(ScriptContext.class.getResource("/symbol-polyfill.js")), context);
            engine.eval(new URLReader(ScriptContext.class.getResource("/es6-shim.min.js")), context);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
//        context.setAttribute("document", WebIDLAdapter.obtain(this, new com.earnix.webk.script.html.impl.DocumentImpl(panel)), ENGINE_SCOPE);
//        context.setAttribute("console", console, ENGINE_SCOPE);
//        context.setAttribute("setInterval", new Function<>(this, (ctx, args) -> {
//            val fn = (JSObject) args[0];
//            double interval = (double) ScriptUtils.convert(args[1], Double.class);
//
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    fn.call(ctx);
//                    panel.relayout();
//                }
//            }, 0, (long) interval);
//            return null;
//        }, "setInterval"), ENGINE_SCOPE);
//
//
//        context.setAttribute("location", new Location(), ENGINE_SCOPE);
//
////        context.setAttribute("HTMLCanvasElement", new Location(), ENGINE_SCOPE);
//
//        context.setAttribute("addEventListener", new Function<>(this, (ctx, arg) -> {
//            log.trace("addEventListener");
//            return null;
//        }, "addEventListener"), ENGINE_SCOPE);
//
//        context.setAttribute("alert", new Function<>(this, (ctx, arg) -> {
//            if (arg.length == 0) {
//                return null;
//            }
//            JOptionPane.showMessageDialog(panel, String.valueOf(arg[0]));
//            return null;
//        }, "alert"), ENGINE_SCOPE);
//
//

        expose(CanvasGradientImpl.class);
        expose(CanvasPatternImpl.class);
        expose(XMLHttpRequestImpl.class);

        window = new WindowImpl(this);
        windowAdapter = WebIDLAdapter.obtain(this, window);

        windowAdapter.keySet().forEach(key -> {
            context.setAttribute(key, windowAdapter.getMember(key), ENGINE_SCOPE);
        });

        try {
            context.setAttribute("window", engine.eval("this"), ENGINE_SCOPE);
            context.setAttribute("self", engine.eval("this"), ENGINE_SCOPE);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    private void expose(Class implementationClass) {

        ClassUtils.getAllInterfaces(implementationClass).forEach(i -> {
            if (i.isAnnotationPresent(Exposed.class)) {
                context.setAttribute(i.getSimpleName(), new AbstractJSObject() {
                    @Override
                    public Object newObject(Object... args) {
                        try {

                            val target = implementationClass.newInstance();
                            val adapter = WebIDLAdapter.obtain(ScriptContext.this, target);
                            // todo check here @Constructor annotation
                            FunctionAdapter constructor = (FunctionAdapter) adapter.getMember("constructor");
                            if (constructor != null) {
                                constructor.call(this, args);
                            }
                            return adapter;
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException();
                        }
                    }

                    @Override
                    public String getClassName() {
                        return i.getSimpleName();
                    }
                }, ENGINE_SCOPE);
            }
        });

    }

    private void handleNewDocument() {
        val nextDocument = panel.getDocument();
        if (nextDocument != document) {
            
            if(document != null){
                dispatchUnloadEvents();
            }
            
            initEngine();

            window.setDocument(new DocumentImpl(this));
            context.setAttribute("document", windowAdapter.getMember("document"), ENGINE_SCOPE);

            val scripts = panel.getDocument().getElementsByTag("script");
            log.trace("Document has {} scripts", scripts.size());
            for (int i = 0; i < scripts.size(); i++) {
                val script = scripts.get(i);
                if (StringUtils.isNotBlank(script.data())) {
                    try {
//                                log.trace("Evaluating script {} {}", System.lineSeparator(), script.data());
                        eval(script.data());
                    } catch (Exception e) {
                        log.warn("script.eval", e);
                    }
                } else {
                    val scriptUri = script.attributes().get("src");
                    if (StringUtils.isNotBlank(scriptUri)) {
                        try {
                            val scriptText = panel.getSharedContext().getUac().getScriptResource(scriptUri);
//                                    log.trace("Evaluating script {} {}", System.lineSeparator(), scriptText);
                            eval(scriptText);
                        } catch (RuntimeException e) {
                            log.debug("script.src", e);
                        }
                    }
                }

                dispatchLoadEvents();
            }
            document = nextDocument;
        }
    }

    public Object eval(String scr) {
        Object res;
        try {
            res = engine.eval(scr, context);
        } catch (ScriptException e) {
            if (e.getCause() instanceof NashornException) {
                log.error(NashornException.getScriptStackString(e.getCause()));
            }
            throw new RuntimeException(e);
        }
        panel.reset();
        return res;
    }


    public WindowImpl getWindow() {
        return window;
    }

    ;
}
