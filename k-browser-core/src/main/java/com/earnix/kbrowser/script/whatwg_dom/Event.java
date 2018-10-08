package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.future.AudioWorker;
import com.earnix.kbrowser.script.future.Worker;
import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.Constructor;
import com.earnix.kbrowser.script.web_idl.DOMString;
import com.earnix.kbrowser.script.web_idl.DefaultBoolean;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Optional;
import com.earnix.kbrowser.script.web_idl.ReadonlyAttribute;
import com.earnix.kbrowser.script.web_idl.Sequence;
import com.earnix.kbrowser.script.web_idl.Unforgeable;
import com.earnix.kbrowser.script.web_idl.Unsigned;

/**
 * [Constructor(DOMString type, optional EventInit eventInitDict),
 * Exposed=(Window,Worker,AudioWorklet)]
 * interface Event {
 * readonly attribute DOMString type;
 * readonly attribute EventTarget? target;
 * readonly attribute EventTarget? srcElement; // historical
 * readonly attribute EventTarget? currentTarget;
 * sequence<EventTarget> composedPath();
 * <p>
 * const unsigned short NONE = 0;
 * const unsigned short CAPTURING_PHASE = 1;
 * const unsigned short AT_TARGET = 2;
 * const unsigned short BUBBLING_PHASE = 3;
 * readonly attribute unsigned short eventPhase;
 * <p>
 * void stopPropagation();
 * attribute boolean cancelBubble; // historical alias of .stopPropagation
 * void stopImmediatePropagation();
 * <p>
 * readonly attribute boolean bubbles;
 * readonly attribute boolean cancelable;
 * attribute boolean returnValue;  // historical
 * void preventDefault();
 * readonly attribute boolean defaultPrevented;
 * readonly attribute boolean composed;
 * <p>
 * [Unforgeable] readonly attribute boolean isTrusted;
 * readonly attribute DOMHighResTimeStamp timeStamp;
 * <p>
 * void initEvent(DOMString type, optional boolean bubbles = false, optional boolean cancelable = false); // historical
 * };
 *
 * @author Taras Maslov
 * 6/18/2018
 */
@Constructor(true)
@Exposed({Window.class, Worker.class, AudioWorker.class})
public interface Event {

    void construct(@DOMString String type, @Optional EventInit init);

    @ReadonlyAttribute
    @DOMString String type();

    @Optional
    @ReadonlyAttribute
    EventTarget target();

    @Optional
    @ReadonlyAttribute
    EventTarget srcElement(); // historical

    @Optional
    @ReadonlyAttribute
    EventTarget currentTarget();

    Sequence<EventTarget> composedPath();

    @Unsigned short NONE = 0;
    @Unsigned short CAPTURING_PHASE = 1;
    @Unsigned short AT_TARGET = 2;
    @Unsigned short BUBBLING_PHASE = 3;

    @Unsigned
    @ReadonlyAttribute
    Short eventPhase();

    void stopPropagation();

    Attribute<Boolean> cancelBubble(); // historical alias of .stopPropagation

    void stopImmediatePropagation();

    @ReadonlyAttribute
    boolean bubbles();

    @ReadonlyAttribute
    boolean cancelable();

    @ReadonlyAttribute
    boolean returnValue();  // historical

    void preventDefault();

    @ReadonlyAttribute
    boolean defaultPrevented();

    @ReadonlyAttribute
    boolean composed();

    @Unforgeable
    @ReadonlyAttribute
    boolean isTrusted();

    @ReadonlyAttribute
    DOMHighResTimeStamp timeStamp();

    void initEvent(@DOMString String type, @Optional @DefaultBoolean(false) boolean bubbles, @Optional @DefaultBoolean(false) boolean cancelable); // historical
}
