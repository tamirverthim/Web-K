package com.earnix.webk.script;

import com.earnix.webk.render.Box;
import com.earnix.webk.script.html.impl.DocumentImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.ui_events.FocusEventInit;
import com.earnix.webk.script.ui_events.MouseEventInit;
import com.earnix.webk.script.ui_events.impl.FocusEventImpl;
import com.earnix.webk.script.ui_events.impl.MouseEventImpl;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.impl.EventImpl;
import com.earnix.webk.script.whatwg_dom.impl.EventManager;
import com.earnix.webk.script.whatwg_dom.impl.ScriptDOMFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import javax.swing.*;
import java.awt.event.*;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MouseEventsAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {

    final EventManager eventManager;
    final ScriptContext context;

    Box focusHolder;

    public MouseEventsAdapter(EventManager eventManager, ScriptContext context) {
        this.eventManager = eventManager;
        this.context = context;
    }

    private Event createEvent(String type) {
        return new EventImpl(type, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        val box = context.getPanel().find(e.getX(), e.getY());
        
        if (box != null) {
            Element element = ScriptDOMFactory.getElement(box.getElement());
            
            
            MouseEventInit init = new MouseEventInit();
            init.view = context.getWindow();
            init.button = getButton(e);
            init.bubbles = true;
            init.clientX = e.getX();
            init.clientY = e.getY();
            init.screenX = e.getXOnScreen();
            init.screenY = e.getYOnScreen();
            MouseEventImpl event = new MouseEventImpl("click", init);
            event.setTarget(element);
            eventManager.publishEvent(element, event);


            if (box != focusHolder) {

                Element loser = null;
                if (focusHolder != null) {
                    loser = ScriptDOMFactory.getElement(box.getElement());
                }
                focusIn(element, loser);
                focusHolder = box;
                DocumentImpl documentImpl = (DocumentImpl) context.getWindow().document();
                documentImpl.setActiveElement((ElementImpl) element);
                focus(element, loser);

            }
        }


    }
    
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    /**
     * Type	focusout
     * Interface	FocusEvent
     * Sync / Async	Sync
     * Bubbles	Yes
     * Trusted Targets	Window, Element
     * Cancelable	No
     * Composed	Yes
     * Default action	None
     * Context
     * (trusted events)
     * Event.target : event target losing focus
     * UIEvent.view : Window
     * UIEvent.detail : 0
     * FocusEvent.relatedTarget : event target receiving focus.
     */
    private void focusOut(Element gainer, Element loser) {

        FocusEventInit eventInit = new FocusEventInit();
        eventInit.view = context.getWindow();
        eventInit.bubbles = false;
        eventInit.cancelable = false;
        eventInit.composed = true;

        FocusEventImpl focusEvent = new FocusEventImpl("focusout", eventInit);

        focusEvent.setRelatedTarget(gainer);

        eventManager.publishEvent(loser, focusEvent);
    }

    /**
     * Type	focusin
     * Interface	FocusEvent
     * Sync / Async	Sync
     * Bubbles	Yes
     * Trusted Targets	Window, Element
     * Cancelable	No
     * Composed	Yes
     * Default action	None
     * Context
     * (trusted events)
     * Event.target : event target receiving focus
     * UIEvent.view : Window
     * UIEvent.detail : 0
     * FocusEvent.relatedTarget : event target losing focus (if any).
     */
    private void focusIn(Element gainer, Element loser) {

        FocusEventInit eventInit = new FocusEventInit();
        eventInit.view = context.getWindow();
        eventInit.bubbles = false;
        eventInit.cancelable = false;
        eventInit.composed = true;

        FocusEventImpl focusEvent = new FocusEventImpl("focusin", eventInit);
        focusEvent.setRelatedTarget(loser);

        eventManager.publishEvent(gainer, focusEvent);
    }

    /**
     * Type	focus
     * Interface	FocusEvent
     * Sync / Async	Sync
     * Bubbles	No
     * Trusted Targets	Window, Element
     * Cancelable	No
     * Composed	Yes
     * Default action	None
     * Context
     * (trusted events)
     * Event.target : event target receiving focus
     * UIEvent.view : Window
     * UIEvent.detail : 0
     * FocusEvent.relatedTarget : event target losing focus (if any).
     */
    private void focus(Element gainer, Element loser) {

        FocusEventInit eventInit = new FocusEventInit();
        eventInit.view = context.getWindow();
        eventInit.bubbles = false;
        eventInit.cancelable = false;
        eventInit.composed = true;

        FocusEventImpl focusEvent = new FocusEventImpl("focus", eventInit);
        focusEvent.setRelatedTarget(loser);

        eventManager.publishEvent(gainer, focusEvent);
    }
    
    
    /**
     * 0 MUST indicate the primary button of the device (in general, the left button or the only button on single-button
     * devices, used to activate a user interface control or select text) or the un-initialized value.
     * <p>
     * 1 MUST indicate the auxiliary button (in general, the middle button, often combined with a mouse wheel).
     * <p>
     * 2 MUST indicate the secondary button (in general, the right button, often used to display a context menu).
     * <p>
     * 3 MUST indicate the X1 (back) button.
     * <p>
     * 4 MUST indicate the X2 (forward) button.
     */
    private short getButton(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            return 0;
        } else if (SwingUtilities.isMiddleMouseButton(event)) {
            return 1;
        } else if (SwingUtilities.isRightMouseButton(event)) {
            return 2;
        } else {
            // todo
            throw new IllegalStateException();
        }
    }
    
    
}
