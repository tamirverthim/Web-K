package com.earnix.webk.script;

import com.earnix.webk.render.Box;
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
    
    
    
//
//    @Override
//    public void onMouseClick(BasicPanel basicPanel, Box box) {
//        
//        eventManager.publishEvent(box.getElement(), "click");
//    }
//
//    @Override
//    public void onMouseOver(BasicPanel panel, Box box) {
//        eventManager.publishEvent(box.getElement(), "mouseover");
//    }
//
//    @Override
//    public void onMouseOut(BasicPanel panel, Box box) {
//
//    }
//
//    @Override
//    public void onMouseUp(BasicPanel panel, Box box) {
//
//    }
//
//    @Override
//    public void onMousePressed(BasicPanel panel, MouseEvent e) {
//
//    }
//
//    @Override
//    public void onMouseDragged(BasicPanel panel, MouseEvent e) {
//
//    }
//
//    @Override
//    public void reset() {
//
//    }
    
    private Event createEvent(String type) {
        return new EventImpl(type, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
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
