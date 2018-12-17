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
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import javax.swing.SwingUtilities;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MouseEventsAdapter implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    final EventManager eventManager;
    final ScriptContext context;
    final BasicPanel panel;

    Box focusHolderBox;
    Box hoveredBox;

    Set<Integer> pressedKeys = new HashSet<>();
    Set<Integer> pressedMouseButtons = new HashSet<>();

    public MouseEventsAdapter(EventManager eventManager, ScriptContext context) {
        this.eventManager = eventManager;
        this.context = context;
        panel = context.getPanel();
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        panel.addMouseWheelListener(this);
        panel.addKeyListener(this);
    }

    private Event createEvent(String type) {
        return new EventImpl(type, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        val box = context.getPanel().find(e.getX(), e.getY());

        if (box != null) {
            Element element = ScriptDOMFactory.getElement(box.getElement());

            if (SwingUtilities.isLeftMouseButton(e)) {
                click(element, e);
                if (e.getClickCount() == 2) {
                    dblclick(element, e);
                }
            } else {
                auxclick(element, e);
            }

            // focus
            // https://www.w3.org/TR/uievents/#events-focusevent-event-order
            if (box != focusHolderBox) {
                Element loser = null;
                if (focusHolderBox != null) {
                    loser = ScriptDOMFactory.getElement(box.getElement());
                }

                focusIn(element, loser);

                if (loser != null) {
                    focusOut(element, loser);
                }

                focusHolderBox = box;
                DocumentImpl documentImpl = (DocumentImpl) context.getWindow().document();
                documentImpl.setActiveElement((ElementImpl) element);

                focus(element, loser);
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressedMouseButtons.add(e.getButton());
        Box box = panel.find(e.getX(), e.getY());
        if (box != null) {
            Element element = ScriptDOMFactory.getElement(box.getElement());
            mousedown(element, e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressedMouseButtons.remove(e.getButton());
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
        Box box = panel.find(e.getX(), e.getY());
        if (hoveredBox != box) {

            if (hoveredBox != null) {
                mouseleave(getElement(hoveredBox), e);
                mouseout(getElement(hoveredBox), e);
            }

            if (box != null) {
                Element element = getElement(box);
                mouseenter(element, e);
                mouseover(element, e);
            }

            hoveredBox = box;
        }

        if (box != null) {
            mousemove(getElement(box), e);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

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

    // region mouse events

    private void auxclick(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = awtEvent.getClickCount();
        MouseEventImpl mouseEvent = createMouseEventImpl("auxclick", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    private void click(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = awtEvent.getClickCount();
        MouseEventImpl mouseEvent = createMouseEventImpl("click", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }


    private void dblclick(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = awtEvent.getClickCount();
        MouseEventImpl mouseEvent = createMouseEventImpl("dblclick", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    private void mousedown(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = awtEvent.getClickCount() + 1;
        MouseEventImpl mouseEvent = createMouseEventImpl("mousedown", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }


    private void mouseenter(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = false;
        init.cancelable = false;
        init.composed = false;
        init.detail = 0;
        MouseEventImpl mouseEvent = createMouseEventImpl("mouseenter", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    private void mouseleave(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = false;
        init.cancelable = false;
        init.composed = false;
        init.detail = 0;
        MouseEventImpl mouseEvent = createMouseEventImpl("mouseleve", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    private void mousemove(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = 0;
        MouseEventImpl mouseEvent = createMouseEventImpl("mousemove", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    private void mouseout(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = 0;
        MouseEventImpl mouseEvent = createMouseEventImpl("mouseout", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    private void mouseover(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = 0;
        MouseEventImpl mouseEvent = createMouseEventImpl("mouseenter", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    // endregion


    private MouseEventInit createMouseEventInit(MouseEvent awtEvent) {
        MouseEventInit init = new MouseEventInit();
        init.screenX = awtEvent.getXOnScreen();
        init.screenY = awtEvent.getYOnScreen();
        init.clientX = awtEvent.getX();
        init.clientY = awtEvent.getY();
        init.button = getButton(awtEvent);
        init.buttons = translatePressedMouseButtons();
        init.view = context.getWindow();
        return init;
    }

    private MouseEventImpl createMouseEventImpl(String type, MouseEventInit init, MouseEvent awtEvent) {
        MouseEventImpl mouseEventImpl = new MouseEventImpl(type, init);
        mouseEventImpl.setAlt(pressedKeys.contains(KeyEvent.VK_ALT));
        mouseEventImpl.setShift(pressedKeys.contains(KeyEvent.VK_SHIFT));
        mouseEventImpl.setCtrl(pressedKeys.contains(KeyEvent.VK_CONTROL));
        mouseEventImpl.setMeta(pressedKeys.contains(KeyEvent.VK_META));

        return mouseEventImpl;
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
            return 0; // not sure
        }
    }

    private short translatePressedMouseButtons() {
        short result = 0;
        if (pressedMouseButtons.contains(MouseEvent.BUTTON1)) {
            result += 1;
        }
        if (pressedMouseButtons.contains(MouseEvent.BUTTON2)) {
            result += 4;
        }
        if (pressedMouseButtons.contains(MouseEvent.BUTTON3)) {
            result += 2;
        }
        return result;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    private Element getElement(Box box) {
        return ScriptDOMFactory.getElement(box.getElement());
    }
}
