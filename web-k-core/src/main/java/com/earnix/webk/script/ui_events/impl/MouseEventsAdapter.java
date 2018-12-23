package com.earnix.webk.script.ui_events.impl;

import com.earnix.webk.render.Box;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.html.impl.DocumentImpl;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.ui_events.FocusEventInit;
import com.earnix.webk.script.ui_events.MouseEventInit;
import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.impl.EventManager;
import com.earnix.webk.script.whatwg_dom.impl.ScriptDOMFactory;
import com.earnix.webk.swing.BasicPanel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MouseEventsAdapter implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    final EventManager eventManager;
    final ScriptContext context;
    final BasicPanel panel;
//    private final JComponent target;

    Box focusHolderBox;
    @Getter
    Box hoveredBox;
    @Getter
    MouseEvent lastAwtMouseEvent;
    JFrame currentFrame;

    FrameEventsListener frameEventsListener;

    Set<Integer> pressedKeys = new HashSet<>();
    Set<Integer> pressedMouseButtons = new HashSet<>();

    public MouseEventsAdapter(ScriptContext context) {
        this.eventManager = context.getEventManager();
        this.context = context;
        panel = context.getPanel();
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
//        panel.addMouseWheelListener(this);
        panel.addKeyListener(this);
        addChildrenListeners();

        frameEventsListener = new FrameEventsListener(context, this);

        panel.addHierarchyListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getRoot(panel);
            if (frame != currentFrame) {
                if (currentFrame != null) {
                    currentFrame.removeWindowListener(frameEventsListener);
                }
                if (frame != null) {
                    frame.addWindowListener(frameEventsListener);
                }
                currentFrame = frame;
            }
        });

    }

    public void addChildrenListeners() {
        List<JComponent> children = new ArrayList<>();
        getAllChildren(panel, children);
        children.forEach(child -> {
            child.addMouseListener(this);
            child.addMouseMotionListener(this);
            child.addMouseWheelListener(this);
            child.addKeyListener(this);
        });
    }

    private void getAllChildren(JComponent component, List<JComponent> target) {
        Stream.of(component.getComponents()).filter(c -> c instanceof JComponent).map(c -> (JComponent) c).forEach(child -> {
            target.add(child);
            getAllChildren(child, target);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = SwingUtilities.convertPoint((JComponent) e.getSource(), e.getX(), e.getY(), panel);
        val box = context.getPanel().find(point.x, point.y);

        if (box != null) {
            Element element = ScriptDOMFactory.getElement(context, box.getElement());

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
                    loser = ScriptDOMFactory.getElement(context, box.getElement());
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

        lastAwtMouseEvent = e;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressedMouseButtons.add(e.getButton());
        Box box = panel.find(e.getX(), e.getY());
        if (box != null) {
            Element element = ScriptDOMFactory.getElement(context, box.getElement());
            mousedown(element, e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressedMouseButtons.remove(e.getButton());

        mouseup(getElement(hoveredBox), e);

        lastAwtMouseEvent = e;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        lastAwtMouseEvent = e;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (hoveredBox != null) {
            val el = getElement(hoveredBox);
            mouseleave(el, e);
            mouseout(el, e);
        }
        lastAwtMouseEvent = e;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        handleMouseMove(e);
        lastAwtMouseEvent = e;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        handleMouseMove(e);
        lastAwtMouseEvent = e;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        lastAwtMouseEvent = e;
    }

    private void handleMouseMove(MouseEvent e) {
        Point location = SwingUtilities.convertPoint((JComponent) (e.getSource()), e.getPoint(), panel);
        val box = panel.find(location.x, location.y);
        val boxElementModel = box != null ? box.getElement() : null;
        val hoverElementModel = hoveredBox != null ? hoveredBox.getElement() : null;

        if (!Objects.equals(boxElementModel, hoverElementModel)) {

            log.error("Box updated, next {}", box);

            if (hoverElementModel != null) {
                log.trace("leaving {}", hoveredBox.getElement());
                mouseleave(getElement(hoveredBox), e);
                mouseout(getElement(hoveredBox), e);
            }

            if (box != null) {
                log.trace("entering {}", box.getElement());
                Element element = getElement(box);
                mouseenter(element, e);
                mouseover(element, e);
            }
        }

        hoveredBox = box;

        if (box != null) {
            mousemove(getElement(box), e);
        }

        lastAwtMouseEvent = e;
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

    void mouseleave(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = false;
        init.cancelable = false;
        init.composed = false;
        init.detail = 0;
        MouseEventImpl mouseEvent = createMouseEventImpl("mouseleave", init, awtEvent);
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

    void mouseout(Element target, MouseEvent awtEvent) {
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
        MouseEventImpl mouseEvent = createMouseEventImpl("mouseover", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }


    private void mouseup(Element target, MouseEvent awtEvent) {
        MouseEventInit init = createMouseEventInit(awtEvent);
        init.bubbles = true;
        init.cancelable = true;
        init.composed = true;
        init.detail = awtEvent.getClickCount() + 1;
        MouseEventImpl mouseEvent = createMouseEventImpl("mouseup", init, awtEvent);
        eventManager.publishEvent(target, mouseEvent);
    }

    // endregion


    private MouseEventInit createMouseEventInit(MouseEvent awtEvent) {
        MouseEventInit init = new MouseEventInit();
        Point locationOnScreen = SwingUtilities.convertPoint((JComponent) awtEvent.getSource(), awtEvent.getLocationOnScreen(), panel);
        Point location = SwingUtilities.convertPoint((JComponent) (awtEvent.getSource()), awtEvent.getPoint(), panel);
        init.screenX = locationOnScreen.x;
        init.screenY = locationOnScreen.y;
        init.clientX = location.x;
        init.clientY = location.y;
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
        if (box == null) {
            return null;
        }
        return ScriptDOMFactory.getElement(context, box.getElement());
    }
}
