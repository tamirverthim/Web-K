package com.earnix.webk.script.html.impl;

import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.console.Console;
import com.earnix.webk.script.console.impl.ConsoleImpl;
import com.earnix.webk.script.cssom.CSSStyleDeclaration;
import com.earnix.webk.script.cssom.impl.CSSStyleDeclarationImpl;
import com.earnix.webk.script.fetch.RequestInfo;
import com.earnix.webk.script.fetch.RequestInit;
import com.earnix.webk.script.fetch.Response;
import com.earnix.webk.script.future.Promise;
import com.earnix.webk.script.html.ApplicationCache;
import com.earnix.webk.script.html.BarProp;
import com.earnix.webk.script.html.CustomElementRegistry;
import com.earnix.webk.script.html.Document;
import com.earnix.webk.script.html.History;
import com.earnix.webk.script.html.ImageBitmap;
import com.earnix.webk.script.html.ImageBitmapOptions;
import com.earnix.webk.script.html.ImageBitmapSource;
import com.earnix.webk.script.html.Location;
import com.earnix.webk.script.html.Navigator;
import com.earnix.webk.script.html.TimerHandler;
import com.earnix.webk.script.html.Window;
import com.earnix.webk.script.html.WindowPostMessageOptions;
import com.earnix.webk.script.html.WindowProxy;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import com.earnix.webk.script.web_idl.Function;
import com.earnix.webk.script.web_idl.Sequence;
import com.earnix.webk.script.web_idl.VoidFunction;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import com.earnix.webk.script.whatwg_dom.impl.EventTargetImpl;
import com.earnix.webk.script.whatwg_dom.impl.Level1EventTarget;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class WindowImpl implements Window {

    final ScriptContext scriptContext;
    @Setter DocumentImpl document;
    final NavigatorImpl navigator = new NavigatorImpl();
    final LocationImpl location;
    ConsoleImpl console = new ConsoleImpl();
    @Delegate(types = EventTarget.class)

    EventTargetImpl eventTargetImpl;
    Level1EventTarget level1EventTarget;

    public WindowImpl(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
        location = new LocationImpl(scriptContext.getPanel());
        eventTargetImpl = new EventTargetImpl(scriptContext);
        level1EventTarget = new Level1EventTarget(scriptContext, eventTargetImpl);
    }

    @Override
    public WindowProxy window() {
        return new WindowProxyImpl(this);
    }

    @Override
    public WindowProxy self() {
        return null;
    }

    @Override
    public Document document() {
        return document;
    }

    @Override
    public @DOMString Attribute<String> name() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return null;
            }

            @Override
            public void set(String s) {

            }
        };
    }

    @Override
    public Location location() {
        return null;
    }

    @Override
    public History history() {
        return null;
    }

    @Override
    public CustomElementRegistry customElements() {
        return null;
    }

    @Override
    public BarProp locationbar() {
        return null;
    }

    @Override
    public BarProp menubar() {
        return null;
    }

    @Override
    public BarProp personalbar() {
        return null;
    }

    @Override
    public BarProp scrollbars() {
        return null;
    }

    @Override
    public BarProp statusbar() {
        return null;
    }

    @Override
    public BarProp toolbar() {
        return null;
    }

    private String status;
    private Attribute<String> statusAttribute = new Attribute<String>() {

        @Override
        public String get() {
            return status;
        }

        @Override
        public void set(String s) {
            WindowImpl.this.status = status;
        }
    };

    @Override
    public @DOMString Attribute<String> status() {
        return statusAttribute;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean closed() {
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public void focus() {

    }

    @Override
    public void blur() {

    }

    @Override
    public WindowProxy frames() {
        return null;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public WindowProxy top() {
        return null;
    }

    @Override
    public Attribute<Object> opener() {
        return new Attribute<Object>() {
            @Override
            public Object get() {
                return null;
            }

            @Override
            public void set(Object o) {

            }
        };
    }

    @Override
    public WindowProxy parent() {
        return null;
    }

    @Override
    public Element frameElement() {
        return null;
    }

    @Override
    public WindowProxy open(String url, @DOMString String target, @DOMString String features) {
        return null;
    }

    @Override
    public Object get(@DOMString String name) {
        return null;
    }

    @Override
    public Navigator navigator() {
        return null;
    }

    @Override
    public ApplicationCache applicationCache() {
        return null;
    }

    @Override
    public void alert() {
        alert("");
    }

    @Override
    public void alert(@DOMString String message) {
        JOptionPane.showMessageDialog(scriptContext.getPanel(), message);
    }

    @Override
    public boolean confirm(@DOMString String message) {
        return JOptionPane.showConfirmDialog(
                scriptContext.getPanel(),
                message,
                "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    @Override
    public @DOMString String prompt(@DOMString String message, @DOMString String _default) {
        return JOptionPane.showInputDialog(scriptContext.getPanel(), message, "Prompt", JOptionPane.QUESTION_MESSAGE);
    }

    @Override
    public void print() {

    }

    @Override
    public void postMessage(Object message, String targetOrigin, Sequence<Object> transfer) {

    }

    @Override
    public void postMessage(Object message, WindowPostMessageOptions options) {

    }

    private HashMap<String, Attribute<EventHandler>> handlers = new HashMap<>();

    private Attribute<EventHandler> getEventHandler(String event) {
        return level1EventTarget.getHandlerAttribute(event);
    }


    @Override
    public Attribute<EventHandler> onabort() {
        return getEventHandler("onabort");
    }

    @Override
    public Attribute<EventHandler> onauxclick() {
        return getEventHandler("onauxclick");
    }

    @Override
    public Attribute<EventHandler> onblur() {
        return getEventHandler("onblur");
    }

    @Override
    public Attribute<EventHandler> oncancel() {
        return getEventHandler("oncancel");
    }

    @Override
    public Attribute<EventHandler> oncanplay() {
        return getEventHandler("oncanplay");
    }

    @Override
    public Attribute<EventHandler> oncanplaythrough() {
        return getEventHandler("oncanplaythrough");
    }

    @Override
    public Attribute<EventHandler> onchange() {
        return getEventHandler("onchange");
    }

    @Override
    public Attribute<EventHandler> onclick() {
        return getEventHandler("onclick");
    }

    @Override
    public Attribute<EventHandler> onclose() {
        return getEventHandler("onclose");
    }

    @Override
    public Attribute<EventHandler> oncontextmenu() {
        return getEventHandler("oncontextmenu");
    }

    @Override
    public Attribute<EventHandler> oncuechange() {
        return getEventHandler("oncuechange");
    }

    @Override
    public Attribute<EventHandler> ondblclick() {
        return getEventHandler("ondblclick");
    }

    @Override
    public Attribute<EventHandler> ondrag() {
        return getEventHandler("ondrag");
    }

    @Override
    public Attribute<EventHandler> ondragend() {
        return getEventHandler("ondragend");
    }

    @Override
    public Attribute<EventHandler> ondragenter() {
        return getEventHandler("ondragenter");
    }

    @Override
    public Attribute<EventHandler> ondragexit() {
        return getEventHandler("ondragexit");
    }

    @Override
    public Attribute<EventHandler> ondragleave() {
        return getEventHandler("ondragleave");
    }

    @Override
    public Attribute<EventHandler> ondragover() {
        return getEventHandler("ondragover");
    }

    @Override
    public Attribute<EventHandler> ondragstart() {
        return getEventHandler("ondragstart");
    }

    @Override
    public Attribute<EventHandler> ondrop() {
        return getEventHandler("ondrop");
    }

    @Override
    public Attribute<EventHandler> ondurationchange() {
        return getEventHandler("ondurationchange");
    }

    @Override
    public Attribute<EventHandler> onemptied() {
        return getEventHandler("onemptied");
    }

    @Override
    public Attribute<EventHandler> onended() {
        return getEventHandler("onended");
    }

    @Override
    public Attribute<EventHandler> onfocus() {
        return getEventHandler("onfocus");
    }

    @Override
    public Attribute<EventHandler> oninput() {
        return getEventHandler("oninput");
    }

    @Override
    public Attribute<EventHandler> oninvalid() {
        return getEventHandler("oninvalid");
    }

    @Override
    public Attribute<EventHandler> onkeydown() {
        return getEventHandler("onkeydown");
    }

    @Override
    public Attribute<EventHandler> onkeypress() {
        return getEventHandler("onkeypress");
    }

    @Override
    public Attribute<EventHandler> onkeyup() {
        return getEventHandler("onkeyup");
    }


//    EventHandler onladValue = null;
//    Attribute<EventHandler> onload = new Attribute<EventHandler>() {
//        @Override
//        public EventHandler get() {
//            return onladValue;
//        }
//
//        @Override
//        public void set(EventHandler eventHandler) {
//            onladValue = eventHandler;
//        }
//    };

    @Override
    public Attribute<EventHandler> onload() {
        return getEventHandler("onload");
    }

    @Override
    public Attribute<EventHandler> onloadeddata() {
        return getEventHandler("onloadeddata");
    }

    @Override
    public Attribute<EventHandler> onloadedmetadata() {
        return getEventHandler("onloadedmetadata");
    }

    @Override
    public Attribute<EventHandler> onloadend() {
        return getEventHandler("onloadend");
    }

    @Override
    public Attribute<EventHandler> onloadstart() {
        return getEventHandler("onloadstart");
    }

    @Override
    public Attribute<EventHandler> onmousedown() {
        return getEventHandler("onmousedown");
    }

    @Override
    public Attribute<EventHandler> onmouseenter() {
        return getEventHandler("onmouseenter");
    }

    @Override
    public Attribute<EventHandler> onmouseleave() {
        return getEventHandler("onmouseleave");
    }

    @Override
    public Attribute<EventHandler> onmousemove() {
        return getEventHandler("onmousemove");
    }

    @Override
    public Attribute<EventHandler> onmouseout() {
        return getEventHandler("onmouseout");
    }

    @Override
    public Attribute<EventHandler> onmouseover() {
        return getEventHandler("onmouseover");
    }

    @Override
    public Attribute<EventHandler> onmouseup() {
        return getEventHandler("onmouseup");
    }

    @Override
    public Attribute<EventHandler> onwheel() {
        return getEventHandler("onwheel");
    }

    @Override
    public Attribute<EventHandler> onpause() {
        return getEventHandler("onpause");
    }

    @Override
    public Attribute<EventHandler> onplay() {
        return getEventHandler("onplay");
    }

    @Override
    public Attribute<EventHandler> onplaying() {
        return getEventHandler("onplaying");
    }

    @Override
    public Attribute<EventHandler> onprogress() {
        return getEventHandler("onprogress");
    }

    @Override
    public Attribute<EventHandler> onratechange() {
        return getEventHandler("onratechange");
    }

    @Override
    public Attribute<EventHandler> onreset() {
        return getEventHandler("onreset");
    }

    @Override
    public Attribute<EventHandler> onresize() {
        return getEventHandler("onresize");
    }

    @Override
    public Attribute<EventHandler> onscroll() {
        return getEventHandler("onscroll");
    }

    @Override
    public Attribute<EventHandler> onsecuritypolicyviolation() {
        return getEventHandler("onsecuritypolicyviolation");
    }

    @Override
    public Attribute<EventHandler> onseeked() {
        return getEventHandler("onseeked");
    }

    @Override
    public Attribute<EventHandler> onseeking() {
        return getEventHandler("onseeking");
    }

    @Override
    public Attribute<EventHandler> onselect() {
        return getEventHandler("onselect");
    }

    @Override
    public Attribute<EventHandler> onstalled() {
        return getEventHandler("onstalled");
    }

    @Override
    public Attribute<EventHandler> onsubmit() {
        return getEventHandler("onsubmit");
    }

    @Override
    public Attribute<EventHandler> onsuspend() {
        return getEventHandler("onsuspend");
    }

    @Override
    public Attribute<EventHandler> ontimeupdate() {
        return getEventHandler("ontimeupdate");
    }

    @Override
    public Attribute<EventHandler> ontoggle() {
        return getEventHandler("ontoggle");
    }

    @Override
    public Attribute<EventHandler> onvolumechange() {
        return getEventHandler("onvolumechange");
    }

    @Override
    public Attribute<EventHandler> onwaiting() {
        return getEventHandler("onwaiting");
    }

    @Override
    public Promise<Response> fetch(RequestInfo input, RequestInit init) {
        return null;
    }

    @Override
    public String origin() {
        return null;
    }

    @Override
    public @DOMString String btoa(@DOMString String data) {
        return null;
    }

    @Override
    public String atob(@DOMString String data) {
        return null;
    }

    // region timers

    Timer timer = new Timer();
    HashMap<Integer, TimerTask> timeoutTasks = new HashMap<>();
    HashMap<Integer, TimerTask> intervalTasks = new HashMap<>();

    @Override
    public int setTimeout(TimerHandler handler, int timeout, Object... arguments) {
        if (handler.is(String.class)) {
            // unimplemented
            return -1;
        } else if (handler.is(com.earnix.webk.script.web_idl.Function.class)) {
            val task = new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        handler.<Function>get().call(this, arguments);
                        scriptContext.getPanel().relayout();
                    });
                }
            };

            timer.schedule(task, (long) timeout);
            val handle = timeoutTasks.size();
            timeoutTasks.put(handle, task);
            return handle;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void clearTimeout(int handle) {
        val task = timeoutTasks.get(handle);
        if (task != null) {
            task.cancel();
            timeoutTasks.remove(handle);
        } else {
            log.trace("No task to cancel for handle {}", handle);
        }
    }

    /**
     * @see WindowImpl#setTimeout(com.earnix.webk.script.html.TimerHandler, int, java.lang.Object...)
     */
    @Override
    public int setInterval(TimerHandler handler, int timeout, Object... arguments) {
        if (handler.is(String.class)) {
            //
            log.warn("Unimplemented");
            return -1;
        } else if (handler.is(com.earnix.webk.script.web_idl.Function.class)) {
            val task = new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        handler.<Function>get().call(this, arguments);
                        scriptContext.getPanel().reset();
                    });
                }
            };
            timer.scheduleAtFixedRate(task, 0, (long) timeout);
            val handle = intervalTasks.size();
            intervalTasks.put(handle, task);
            return handle;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void clearInterval(int handle) {
        val task = intervalTasks.get(handle);
        if (task != null) {
            task.cancel();
            intervalTasks.remove(handle);
        } else {
            log.trace("No task to cancel for handle {}", handle);
        }
    }
    
    public void clearTasks() {
        timer.cancel();
    }

    @Override
    public void queueMicrotask(VoidFunction callback) {

    }

    // endregion

    @Override
    public Promise<ImageBitmap> createImageBitmap(ImageBitmapSource image, ImageBitmapOptions options) {
        return null;
    }

    @Override
    public Promise<ImageBitmap> createImageBitmap(ImageBitmapSource image, long sx, long sy, long sw, long sh, ImageBitmapOptions options) {
        return null;
    }

    @Override
    public Object item(int index) {
        return null;
    }

    @Override
    public Object namedItem(@DOMString String name) {
        return null;
    }

    @Override
    public Attribute<Object> event() {
        return new Attribute<Object>() {
            @Override
            public Object get() {
                return null;
            }

            @Override
            public void set(Object o) {

            }
        };
    }

    @Override
    public Console console() {
        return console;
    }

    @Override
    public CSSStyleDeclaration getComputedStyle(Element elt, String pseudoElt) {
        val element = ((ElementImpl) elt).getModel();
        return new CSSStyleDeclarationImpl(
                scriptContext.getPanel().getSharedContext().getStyle(element).toString(),
                scriptContext);
    }

    private void repaintPanel() {
        SwingUtilities.invokeLater(() -> scriptContext.getPanel().repaint());
    }
}
