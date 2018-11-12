package com.earnix.webk.script;

import com.earnix.webk.script.ui_events.MouseEventImpl;
import com.earnix.webk.script.ui_events.MouseEventInit;
import com.earnix.webk.script.whatwg_dom.Event;
import com.earnix.webk.script.whatwg_dom.impl.EventImpl;
import com.earnix.webk.script.whatwg_dom.impl.EventManager;
import com.earnix.webk.swing.BasicPanel;
import com.earnix.webk.swing.FSMouseListener;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MouseEventsAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {

    EventManager eventManager;
    BasicPanel panel;
    
    private Event createEvent(String type) {
        return new EventImpl(type, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        val box = panel.find(e.getX(), e.getY());
        
        if (box != null) {
            MouseEventInit init = new MouseEventInit();
            init.clientX = e.getX();
            init.clientY = e.getY();
            init.screenX = e.getXOnScreen();
            init.screenY = e.getYOnScreen();
            MouseEventImpl event = new MouseEventImpl("click", init);
            eventManager.publishEvent(box.getElement(), event);
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
}
