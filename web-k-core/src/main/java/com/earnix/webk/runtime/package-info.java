/**
 * Implemented events:
 * - load
 * - unload
 * - focus
 * - focusin
 * - focusout
 * - mouseenter
 * - mouseleave
 * - click
 * - mousemove
 * - mouseup
 * - mousedown
 * (focus is now triggered with mouse click)
 * <p>
 * Not implemented events:
 * - abort
 * - error
 * - select
 * <p>
 * Script evaluation entry points:
 * - {@link com.earnix.webk.runtime.ScriptContext#documentLoaded() }
 * - {@link com.earnix.webk.runtime.dom.impl.EventTargetImpl#dispatchEvent(com.earnix.webk.runtime.dom.Event)};
 * - {@link java.util.TimerTask#run()} at {@link com.earnix.webk.runtime.html.impl.WindowImpl#setTimeout(com.earnix.webk.runtime.html.TimerHandler, int, java.lang.Object...)}
 * - {@link java.util.TimerTask#run()} at {@link com.earnix.webk.runtime.html.impl.WindowImpl#setInterval(com.earnix.webk.runtime.html.TimerHandler, int, java.lang.Object...)}
 *
 * @author Taras Maslov
 * 11/18/2018
 */
package com.earnix.webk.runtime;